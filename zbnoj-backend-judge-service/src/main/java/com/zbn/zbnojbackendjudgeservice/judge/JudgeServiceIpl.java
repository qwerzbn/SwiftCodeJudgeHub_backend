package com.zbn.zbnojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.zbn.zbnbackendcommon.common.ErrorCode;
import com.zbn.zbnbackendcommon.exception.BusinessException;
import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.zbn.zbnojbackendjudgeservice.judge.strategy.JudgeContext;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.zbn.zbnojbackendmodel.codesandbox.JudgeInfo;
import com.zbn.zbnojbackendmodel.dto.question.JudgeCase;
import com.zbn.zbnojbackendmodel.entity.Question;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import com.zbn.zbnojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.zbn.zbnojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceIpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private JudgeManager judgeManager;
    @Value("${CodeSandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        long id = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该提交已经执行过了");
        }
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        questionFeignClient.UpdateQuestionSubmitById(questionSubmit);
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandbox(type);
        CodeSandboxProxy proxy = new CodeSandboxProxy(codeSandbox);
        String JudgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(JudgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        ExecuteCodeRequest request = new ExecuteCodeRequest().builder()
                .language(language)
                .code(code)
                .inputList(inputList).build();
        ExecuteCodeResponse executeCodeResponse = proxy.executeCode(request);
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）更新题目的判题状态和信息
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionFeignClient.UpdateQuestionSubmitById(questionSubmit);
        return questionSubmit;
    }
}
