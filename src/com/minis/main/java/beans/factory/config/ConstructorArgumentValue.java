package com.minis.main.java.beans.factory.config;

import lombok.Data;

/**
 * @description: 用于对bean definition中的构造器注入参数construct-arg进行抽象
 */
@Data
public class ConstructorArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ConstructorArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ConstructorArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }
}
