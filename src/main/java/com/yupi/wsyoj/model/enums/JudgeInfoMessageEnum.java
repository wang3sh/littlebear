package com.yupi.wsyoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息消息枚举
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public enum JudgeInfoMessageEnum {

    ACCEPTED("成功", "ACCEPTED"),
    WRONG_ANSWER("答案错误", "WRONG_ANSWER"),
    COMPILE_ERROR("编译错误", "COMPILE_ERROR"),
    Time_LIMIT_EXCEEDED("TLE", "MEMORY_LIMIT_EXCEEDED"),
    MEMORY_LIMIT_EXCEEDED("MLE", "MEMORY_LIMIT_EXCEEDED"),
    OUTPUT_LIMIT_EXCEEDED("OLE", "OUTPUT_LIMIT_EXCEEDED"),
    DANGEROUS_OPERATION("危险操作", "DANGEROUS_OPERATION"),
    RUNTIME_ERROR("运行错误", "RUNTIME_ERROR"),
    SYSTEM_ERROR("系统错误", "SYSTEM_ERROR"),
    PRESENTATION_ERROR("展示错误", "PRESENTATION_ERROR"),
    WAITING("等待中", "WAITING");

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
