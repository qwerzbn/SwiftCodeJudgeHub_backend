package com.zbn.zbnojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.zbn.zbnojbackendmodel.codesandbox.JudgeInfo;
import com.zbn.zbnojbackendmodel.dto.question.JudgeCase;
import com.zbn.zbnojbackendmodel.dto.question.JudgeConfig;
import com.zbn.zbnojbackendmodel.entity.Question;
import com.zbn.zbnojbackendmodel.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * java语言判题策略
 *
 * @author: zbn
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(judgeInfo.getTime());
        judgeInfoResponse.setMemory(judgeInfo.getMemory());
        // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.Accepted;
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.Wrong_Answer;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < outputList.size(); i++) {
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.Wrong_Answer;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long needTimeLimit = judgeConfig.getTimeLimit();
        long needMemoryLimit = judgeConfig.getMemoryLimit();
        // 假设Java执行时间需要额外耗费1s
        final long JAVA_TIME_COSET = 1000L;
        if (judgeInfo.getTime() - JAVA_TIME_COSET > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.Time_Limit_Exceeded;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (judgeInfo.getMemory() > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.Memory_Limit_Exceeded;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;

    }
}
