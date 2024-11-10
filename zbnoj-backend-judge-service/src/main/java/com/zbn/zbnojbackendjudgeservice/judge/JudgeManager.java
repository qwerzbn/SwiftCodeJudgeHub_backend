package com.zbn.zbnojbackendjudgeservice.judge;

import com.zbn.zbnojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.zbn.zbnojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.zbn.zbnojbackendjudgeservice.judge.strategy.JudgeContext;
import com.zbn.zbnojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.zbn.zbnojbackendmodel.codesandbox.JudgeInfo;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理
 *
 * @author: zbn
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);

    }
}
