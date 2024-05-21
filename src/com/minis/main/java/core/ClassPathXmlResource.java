package com.minis.main.java.core;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @author: qiyijie@mail2.sysu.edu.cn
 * @description: xml格式的beans定义文件
 */
@Slf4j
public class ClassPathXmlResource implements Resource{
    private Iterator<Element> elementIterator;

    public ClassPathXmlResource(String file){
        SAXReader saxReader = new SAXReader();
        try{
            URL xmlPath =
                    this.getClass().getClassLoader().getResource(file);
            Document document = saxReader.read(xmlPath);
            elementIterator = document.getRootElement().elementIterator();
        }catch (DocumentException e){
            log.error("文档路径找不到，日志：{}", e.getMessage());
        }
    }

    @Override
    public boolean hasNext() {
        return elementIterator.hasNext();
    }

    @Override
    public Element next() {
        return elementIterator.next();
    }
}
