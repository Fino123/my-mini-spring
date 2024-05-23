package com.minis.main.java.beans.factory.support;

import com.minis.main.java.beans.*;
import com.minis.main.java.beans.factory.BeanFactory;
import com.minis.main.java.beans.factory.config.BeanDefinition;
import com.minis.main.java.beans.factory.constant.SimpleBeanFactoryConstant;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<>(SimpleBeanFactoryConstant.INIT_BEAN_DEFINITION_REGISTRY_SIZE);
    private final List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects =
            new HashMap<>(SimpleBeanFactoryConstant.INIT_EARLY_SINGLETON_OBJECT_REGISTRY_SIZE);

    // 以下是BeanDefinitionRegistry的方法
    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        // !注意不可以在这里执行lazy init的判断
        // 如果在这里执行lazy init判断后执行getBean函数，可能ref依赖对象的bean definition还不存在，会报NullPointerException
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    //以下为BeanFactory所定义的方法
    @Override
    public Object getBean(String name) {
        Object singleton = this.getSingleton(name);
        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(name);
            if (singleton == null){
                BeanDefinition beanDefinition = this.getBeanDefinition(name);
                singleton = createBean(beanDefinition);
                this.registerSingleton(name, singleton);
                // 执行bean post processor的生命周期
            }
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String name) {
        return this.containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    /**
     * @param beanDefinition BeanDefinition类，存储bean定义的信息
     * @return 经过BeanDefinition构造的对象，涉及缓存依赖问题
     * @author: qiyijie
     */
    private Object createBean(BeanDefinition beanDefinition) {
        // 构造初始对象
        Object initBean = doCreateBean(beanDefinition);

        // 放入二级缓存
        earlySingletonObjects.put(beanDefinition.getId(), initBean);

        try {
            Class<?> clz = Class.forName(beanDefinition.getClassName());
            // 注入属性
            handleProperties(beanDefinition, clz, initBean);
        }catch (ClassNotFoundException e){
            log.error(e.getMessage());
        }

        return initBean;
    }

    /**
     * @author: qiyijie
     * @param beanDefinition bean的定义
     * @return 通过构造器创建好的bean，但是还未执行属性注入
     * @description: 通过构造函数创建一个初始的bean，然而还未执行属性注入，故放到earlySingletonObjects里。
     */
    public Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数，构造器注入
            ArgumentValues argumentValues =
                    beanDefinition.getConstructorArgumentValues();
            //如果有参数
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>
                        [argumentValues.getArgumentCount()];
                Object[] paramValues = new
                        Object[argumentValues.getArgumentCount()];
                //对每一个参数，分数据类型分别处理
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue =
                            argumentValues.getIndexedGenericArgumentValue(i);
                    if (SimpleBeanFactoryConstant.String_TYPE_SET.contains(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if (SimpleBeanFactoryConstant.Integer_TYPE_SET.contains(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] =
                                Integer.valueOf((String) argumentValue.getValue());
                    } else if (SimpleBeanFactoryConstant.int_TYPE_SET.contains(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String)
                                argumentValue.getValue());
                    } else { //默认为string
                        paramTypes[i] = SimpleBeanFactoryConstant.DEFAULT_BEAN_TYPE;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                try {
                    //按照特定构造器创建实例
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (Exception e) {
                    log.error("error when try to instantiate bean, {}", e.getMessage());
                }
            } else { //如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            log.error("error when try to instantiate bean without arguments, {}", e.getMessage());
        }
        return obj;
    }

    /**
     * @author: qiyijie
     * @param bd bean定义
     * @param clz bean的类
     * @param obj bean的初始化对象（还未执行属性注入）
     * @description: 执行bean的属性注入，若对象还不存在，则递归调用getBean方法构造依赖对象
     */
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        // 处理属性
        System.out.println("handle properties for bean : " + bd.getId());
        PropertyValues propertyValues = bd.getPropertyValues();
        //如果有属性
        if (!propertyValues.isEmpty()) {
            for (int i=0; i<propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    //如果不是ref，只是普通属性
                    //对每一个属性，分数据类型分别处理
                    //todo: 可以和上面那个重复的提取成一个函数
                    if (SimpleBeanFactoryConstant.String_TYPE_SET.contains(pType)) {
                        paramTypes[0] = String.class;
                    } else if (SimpleBeanFactoryConstant.Integer_TYPE_SET.contains(pType)) {
                        paramTypes[0] = Integer.class;
                        pValue = Integer.valueOf((String) pValue);
                    } else if (SimpleBeanFactoryConstant.int_TYPE_SET.contains(pType)) {
                        paramTypes[0] = int.class;
                        pValue = Integer.valueOf((String) pValue);
                    } else { // 默认为string
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                }
                else { //is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                    //再次调用getBean创建ref的bean实例
                    paramValues[0] = getBean((String)pValue);
                }

                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                }catch (NoSuchMethodException e){
                    log.error(e.getMessage());
                }
                try {
                    assert method != null;
                    method.invoke(obj, paramValues);
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        }
    }

    public void refresh() throws BeansException {
        for (String beanName : beanDefinitionNames) {
            if (!beanDefinitionMap.get(beanName).isLazyInit())
                getBean(beanName);
        }
    }
}

