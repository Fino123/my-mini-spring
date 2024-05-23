package com.minis.main.java.beans;

import lombok.Data;
import lombok.Getter;

/**
 * @description: 用与对bean definition中的property进行抽象
 */
@Data
public class PropertyValue {
    private final String name;
    private final Object value;
    private final String type;
    private final boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.isRef = isRef;
    }
}
