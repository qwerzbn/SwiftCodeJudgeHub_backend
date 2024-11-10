package com.zbn.zbnojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbn.zbnojbackendmodel.dto.questionSubmit.QuestionSubmitAddRequest;
import com.zbn.zbnojbackendmodel.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import com.zbn.zbnojbackendmodel.entity.User;
import com.zbn.zbnojbackendmodel.vo.QuestionSubmitVO;

/**
 * 题目提交服务
 *
 * @author <a href="https://github.com/qwerzbn">zbn</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 获取题目提交
     *
     * @param loginUser
     * @param questionSubmitAddRequest
     * @return
     */
    long doQuestionSubmit(User loginUser, QuestionSubmitAddRequest questionSubmitAddRequest);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
