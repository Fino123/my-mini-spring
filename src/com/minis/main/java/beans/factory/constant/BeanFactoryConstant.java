package com.minis.main.java.beans.factory.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BeanFactoryConstant {
    // init大小
    public static int INIT_BEAN_DEFINITION_REGISTRY_SIZE = 256;
    public static int INIT_EARLY_SINGLETON_OBJECT_REGISTRY_SIZE = 16;

    // String 类型别名
    public static String[] String_TYPE_NAMES = {"String", "java.lang.String"};
    public static Set<String> String_TYPE_SET = new HashSet<>(Arrays.asList(String_TYPE_NAMES));

    // Integer
    public static String[] Integer_TYPE_NAMES = {"Integer", "java.lang.Integer"};
    public static Set<String> Integer_TYPE_SET = new HashSet<>(Arrays.asList(Integer_TYPE_NAMES));

    // int
    public static String[] int_TYPE_NAMES = {"int"};
    public static Set<String> int_TYPE_SET = new HashSet<>(Arrays.asList(int_TYPE_NAMES));

    //default
    public static Class<?> DEFAULT_BEAN_TYPE = String.class;
}
