package com.zbn.zbnojbackendmodel.codesandbox;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 执行时间（ms）
     */
    private long time;
    /**
     * 运行内存(kb)
     */
    private long memory;
}
