package com.zbn.zbnojbackendquestionservice.controller.inner;

import com.zbn.zbnojbackendmodel.entity.Question;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import com.zbn.zbnojbackendquestionservice.service.QuestionService;
import com.zbn.zbnojbackendquestionservice.service.QuestionSubmitService;
import com.zbn.zbnojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") Long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("question_submit/update")
    @Override
    public Boolean UpdateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}
