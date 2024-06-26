package com.minis.main.java.beans.factory.xml;

import com.minis.main.java.beans.factory.config.ConstructorArgumentValues;
import com.minis.main.java.beans.PropertyValue;
import com.minis.main.java.beans.PropertyValues;
import com.minis.main.java.beans.factory.config.BeanDefinition;
import com.minis.main.java.beans.factory.support.AbstractBeanFactory;
import com.minis.main.java.beans.factory.support.AutowireCapableBeanFactory;
import com.minis.main.java.beans.factory.support.SimpleBeanFactory;
import com.minis.main.java.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {
    private AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String initMethod = element.attributeValue("init-method");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            beanDefinition.setInitMethodName(initMethod);
            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element propertyElement : propertyElements) {
                String propertyName = propertyElement.attributeValue("name");
                String propertyValue = propertyElement.attributeValue("value");
                String propertyType = propertyElement.attributeValue("type");
                String ref = propertyElement.attributeValue("ref");
                boolean isRef = false;
                String finalPropertyValue = null;

                if (propertyValue != null && !propertyValue.equals("")) {
                    // 不是引用类型
                    isRef = false;
                    finalPropertyValue = propertyValue;
                }else if (ref!=null && !ref.equals("")){
                    // 是引用类型
                    isRef = true;
                    finalPropertyValue = ref;
                    refs.add(ref);
                }
                propertyValues.addPropertyValue(new PropertyValue(propertyType, propertyName, finalPropertyValue, isRef));
            }
            beanDefinition.setPropertyValues(propertyValues);
            // 设置依赖项
            beanDefinition.setDependsOn(refs.toArray(new String[]{}));

            // 处理构造器参数
            List<Element> argumentElements = element.elements("construct-arg");
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            for (Element e: argumentElements) {
                String argumentName = e.attributeValue("name");
                String argumentValue = e.attributeValue("value");
                String argumentType = e.attributeValue("type");
                argumentValues.addGenericArgumentValue(argumentValue, argumentType, argumentName);
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
