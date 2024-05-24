package com.minis.main.java.beans.factory.support;

import com.minis.main.java.beans.*;
import com.minis.main.java.beans.factory.BeanFactory;
import com.minis.main.java.beans.factory.config.ConstructorArgumentValue;
import com.minis.main.java.beans.factory.config.ConstructorArgumentValues;
import com.minis.main.java.beans.factory.config.BeanDefinition;
import com.minis.main.java.beans.factory.constant.BeanFactoryConstant;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SimpleBeanFactory extends AbstractBeanFactory {

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        log.info("SimpleBeanFactory do not implements the BeanPostProcessorBeforeInitialization");
        return existingBean;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        log.info("SimpleBeanFactory do not implements the BeanPostProcessorAfterInitialization");
        return existingBean;
    }
}

