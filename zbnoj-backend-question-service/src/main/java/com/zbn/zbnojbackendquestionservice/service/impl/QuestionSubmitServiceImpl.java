package com.zbn.zbnojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbn.zbnbackendcommon.common.ErrorCode;
import com.zbn.zbnbackendcommon.constant.CommonConstant;
import com.zbn.zbnbackendcommon.exception.BusinessException;
import com.zbn.zbnojbackendmodel.dto.questionSubmit.QuestionSubmitAddRequest;
import com.zbn.zbnojbackendmodel.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.zbn.zbnojbackendmodel.entity.Question;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import com.zbn.zbnojbackendmodel.entity.User;
import com.zbn.zbnojbackendmodel.enums.QuestionSubmitLanguageEnum;
import com.zbn.zbnojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.zbn.zbnojbackendmodel.vo.QuestionSubmitVO;
import com.zbn.zbnojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.zbn.zbnojbackendquestionservice.rabbitMq.MyMessageProducer;
import com.zbn.zbnojbackendquestionservice.service.QuestionSubmitService;
import com.zbn.zbnojbackendserviceclient.service.JudgeFeignClient;
import com.zbn.zbnojbackendserviceclient.service.QuestionFeignClient;
import com.zbn.zbnojbackendserviceclient.service.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 题目提交服务实现
 *
 * @author <a href="https://github.com/qwerzbn">zbn</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionFeignClient questionService;
    @Resource
    @Lazy
    private JudgeFeignClient judgeService;
    @Resource
    private MyMessageProducer myMessageProducer;

    @Override
    public long doQuestionSubmit(User loginUser, QuestionSubmitAddRequest questionSubmitAddRequest) {
        // 校验语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 校验题目是否存在
        long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 设置题目提交信息
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(loginUser.getId());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交失败");
        }
        final Long id = questionSubmit.getId();
        // 发送消息到消息队列
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", String.valueOf(id));
        // 异步执行判题
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(id);
        });
        return id;
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Integer status = questionSubmitQueryRequest.getStatus();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        return queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
    }

    @Override
    public QuestionSubmitVO getQuestionVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        long userId = questionSubmit.getUserId();
        // 如果不是本人也不是管理员，那么看不到他人提交的代码
        if (userId != loginUser.getId() && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(),
                questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit ->
                getQuestionVO(questionSubmit, loginUser)).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}
