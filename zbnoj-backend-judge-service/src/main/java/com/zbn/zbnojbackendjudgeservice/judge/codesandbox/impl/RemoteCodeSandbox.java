package com.zbn.zbnojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zbn.zbnbackendcommon.common.ErrorCode;
import com.zbn.zbnbackendcommon.exception.BusinessException;
import com.zbn.zbnojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zbn.zbnojbackendmodel.codesandbox.ExecuteCodeResponse;


/**
 * 远程代码沙箱
 *
 * @Author: zbn
 * @Date: 2024/9/20
 * @Version: 1.0.0
 */
public class RemoteCodeSandbox implements CodeSandbox {
    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "zbn200212";

    /**
     * 调用远程代码沙箱的api
     *
     * @param executeCodeRequest 执行代码请求类
     * @return ExecuteCodeResponse 执行代码响应类
     */
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        if (executeCodeRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String url = "localhost:8090/executeCode";
        String executeCodeRequestStr = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(executeCodeRequestStr)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "远程代码沙箱执行失败");
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}

