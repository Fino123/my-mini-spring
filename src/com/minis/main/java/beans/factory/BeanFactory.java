package com.minis.main.java.beans.factory;

import com.minis.main.java.beans.BeansException;
import com.minis.main.java.beans.factory.config.BeanDefinition;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
