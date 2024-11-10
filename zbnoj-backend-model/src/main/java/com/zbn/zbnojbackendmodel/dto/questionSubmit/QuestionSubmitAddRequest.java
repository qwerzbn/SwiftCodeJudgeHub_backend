package com.zbn.zbnojbackendmodel.dto.questionSubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建题目提交请求
 *
 * @author <a href="https://github.com/qwerzbn">zbn</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}