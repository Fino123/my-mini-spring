package com.minis.main.java.context;

import com.minis.main.java.beans.BeansException;
import com.minis.main.java.beans.factory.BeanFactory;
import com.minis.main.java.beans.factory.config.BeanDefinition;
import com.minis.main.java.beans.factory.support.SimpleBeanFactory;
import com.minis.main.java.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.main.java.core.ClassPathXmlResource;
import com.minis.main.java.core.Resource;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ClassPathXmlApplicationContext implements BeanFactory{
    private final BeanFactory beanFactory;

    //Context类负责利用beanFactory注册对象和获取对象
    public ClassPathXmlApplicationContext(String fileName) {
        //读取beans定义源文件
        Resource resource = new ClassPathXmlResource(fileName);
        //构造bean工厂
        BeanFactory beanFactory = new SimpleBeanFactory();
        //由reader将resource里的bean definition注入bean工厂
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanDefinition);
    }
}
