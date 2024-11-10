package com.zbn.zbnojbackendjudgeservice.judge.codesandbox.impl;

import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.zbn.zbnojbackendmodel.codesandbox.JudgeInfo;
import com.zbn.zbnojbackendmodel.enums.JudgeInfoMessageEnum;
import com.zbn.zbnojbackendmodel.enums.QuestionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.Accepted.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}


