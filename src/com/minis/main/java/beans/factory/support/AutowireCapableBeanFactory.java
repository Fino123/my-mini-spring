package com.minis.main.java.beans.factory.support;

import com.minis.main.java.beans.BeansException;
import com.minis.main.java.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.main.java.beans.factory.config.BeanPostProcessor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    // 在BeanFactory的基础上加了BeanPostProcessor
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    //注册BeanPostProcessor
    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        // 保证同一个类别的BeanPostProcessor只有一个
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanProcessor: getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result,
                    beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }
}
