package com.minis.main.java.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeanDefinition {
    private String id;
    private String className;
}
