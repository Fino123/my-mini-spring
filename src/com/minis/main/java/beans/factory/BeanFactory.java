package com.minis.main.java.beans.factory;

import com.minis.main.java.beans.BeansException;
import com.minis.main.java.beans.factory.config.BeanDefinition;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;
    boolean containsBean(String name);
    boolean isSingleton(String name);
    boolean isPrototype(String name);
    Class<?> getType(String name);
}
