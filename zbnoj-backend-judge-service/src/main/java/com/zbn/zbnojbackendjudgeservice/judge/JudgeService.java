package com.zbn.zbnojbackendjudgeservice.judge;


import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionId);
}
