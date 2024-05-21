package com.minis;

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
public class ClassPathXmlApplicationContext {
    // 存放beanDefinitions
    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();
    // 存放实例，单例模式
    private final Map<String, Object> singletons = new HashMap<>();

    //构造器获取外部配置，解析出Bean的定义，形成内存映像
    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    //读取xml文件
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath =
                    this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            //对配置文件中的每一个<bean>，进行处理
            for (Element element : (List<Element>) rootElement.elements()) {
                //获取Bean的基本信息
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                //将Bean的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        }catch (DocumentException e) {
            log.error("处理xml文档时失败！日志信息：{}", e.getMessage());
        }
    }

    //利用反射创建Bean实例，并存储在singletons中
    private void instanceBeans(){
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(),
                        Class.forName(beanDefinition.getClassName()).newInstance());
            }catch (ClassNotFoundException e){
                log.error("没有找到类：{}, 错误日志：{}", beanDefinition.getClassName(), e.getMessage());
            }catch (InstantiationException | IllegalAccessException e) {
                log.error("instance 初始化失败, {}", e.getMessage());
            }
        }
    }

    //这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
