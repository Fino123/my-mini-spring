package com.minis.main.java.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 用于对bean definition中的构造器注入参数construct-arg进行抽象
 */
@Data
public class ArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }
}
