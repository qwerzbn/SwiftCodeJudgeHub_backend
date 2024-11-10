package com.zbn.zbnojbackendmodel.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目提交编程语言枚举
 *
 * @author <a href="https://github.com/qwerzbn">zbn</a>
 * @date 2024 09 23
 */
public enum JudgeInfoMessageEnum {
    Accepted("Accepted", "成功"),
    Wrong_Answer("Wrong_Answer", "答案错误"),
    Memory_Limit_Exceeded("Memory_Limit_Exceeded", "内存溢出"),
    Time_Limit_Exceeded("Time_Limit_Exceeded", "超时"),
    Presentation_Error("Presentation_Error", "展示错误"),
    Output_Limit_Exceeded("Output_Limit_Exceeded", "输出溢出"),
    Waiting("Waiting", "等待中"),
    Dangerous_Operation("Dangerous_Operation", "危险操作"),
    Runtime_Error("Runtime_Error", "运行错误"),
    System_Error("System_Error", "系统错误"),
    Compile_Error("Compile_Error", "编译错误");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

