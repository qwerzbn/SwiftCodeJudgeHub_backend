package com.zbn.zbnojbackendjudgeservice.judge.strategy;


import com.zbn.zbnojbackendmodel.codesandbox.JudgeInfo;

/**
 * 判题策略
 *
 * @author: 朱贝宁
 */
public interface JudgeStrategy {
    /**
     * 判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
