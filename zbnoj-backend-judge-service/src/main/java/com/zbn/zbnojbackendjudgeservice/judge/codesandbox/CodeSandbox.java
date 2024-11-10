package com.zbn.zbnojbackendjudgeservice.judge.codesandbox;


import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
