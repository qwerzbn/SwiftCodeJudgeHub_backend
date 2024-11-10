package com.zbn.zbnojbackendserviceclient.service;

import com.zbn.zbnojbackendmodel.entity.Question;
import com.zbn.zbnojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 题目服务
 *
 * @author <a href="https://github.com/qwerzbn">zbn</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@FeignClient(name = "zbnoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") Long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId);

    @PostMapping("/question_submit/update")
    Boolean UpdateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}
