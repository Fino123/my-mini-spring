package com.minis.main.java.beans;

import lombok.Data;
import lombok.Getter;

/**
 * @description: 用与对bean definition中的property进行抽象
 */
@Getter
public class PropertyValue {
    private final String name;
    private final Object value;
    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
