package com.zbn.zbnojbackendmodel.dto.question;

import lombok.Data;

/**
 * 判题配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private long timeLimit;

    /**
     * 内存限制（kb）
     */
    private long memoryLimit;
    /**
     * 堆栈限制（kb）
     */
    private long stackLimit;
}
