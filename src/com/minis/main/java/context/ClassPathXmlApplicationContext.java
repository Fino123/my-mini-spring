package com.minis.main.java.context;

import com.minis.main.java.beans.BeansException;
import com.minis.main.java.beans.factory.BeanFactory;
import com.minis.main.java.beans.factory.support.SimpleBeanFactory;
import com.minis.main.java.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.main.java.core.ClassPathXmlResource;
import com.minis.main.java.core.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher{
    private final BeanFactory beanFactory;

    //Context类负责利用beanFactory注册对象和获取对象
    public ClassPathXmlApplicationContext(String fileName) {
        //读取beans定义源文件
        Resource resource = new ClassPathXmlResource(fileName);
        //构造bean工厂
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        //由reader将resource里的bean definition注入bean工厂
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanFactory.getType(name);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
