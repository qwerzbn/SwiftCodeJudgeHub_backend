package com.zbn.zbnojbackendjudgeservice.judge.codesandbox;


import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @author: zhangbaoning
 * 代码沙箱工厂（根据传入的字符串参数选择不同类型的代码沙箱）
 */
public class CodeSandboxFactory {
    public static CodeSandbox createCodeSandbox(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "third":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
