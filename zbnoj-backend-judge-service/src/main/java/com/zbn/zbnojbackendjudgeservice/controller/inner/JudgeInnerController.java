package com.zbn.zbnojbackendjudgeservice.controller.inner;

import com.zbn.zbnojbackendjudgeservice.judge.JudgeService;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import com.zbn.zbnojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;

    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam long questionId) {
        return judgeService.doJudge(questionId);
    }
}
