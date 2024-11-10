package com.zbn.zbnojbackendjudgeservice.judge.strategy;


import com.zbn.zbnojbackendmodel.codesandbox.JudgeInfo;
import com.zbn.zbnojbackendmodel.dto.question.JudgeCase;
import com.zbn.zbnojbackendmodel.entity.Question;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}

