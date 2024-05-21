package com.minis.main.java.core;

import java.util.Iterator;

/**
 * @author: qiyijie@mail2.sysu.edu.cn
 * @description: Resource接口抽象，用于封装beans定义文件。父类接口为Iterator，实现hasNext和next方法，不断读取beansDefinition。
 */
public interface Resource extends Iterator<Object> {

}
