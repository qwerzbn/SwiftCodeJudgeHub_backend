package com.zbn.zbnojbackendjudgeservice.judge.codesandbox.impl;


import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用别人现成开发好的api）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("调用第三方代码沙箱执行代码");
        return null;
    }
}

