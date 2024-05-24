package com.minis.main.java.beans.factory.annotation;

import com.minis.main.java.beans.BeansException;
import com.minis.main.java.beans.factory.config.BeanPostProcessor;
import com.minis.main.java.beans.factory.support.AutowireCapableBeanFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
@Data
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;

    /**
     * @author: qiyijie
     * @description: 扫描bean的属性，若有@Autowired注解，则去beanFactory中寻找对应名称的bean，注入到属性中去
     * @param bean 要注入属性的bean对象
     * @param beanName bean对象的名称
     * @return 注入完@Autowired属性的bean对象
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //对每一个属性进行判断，如果带有@Autowired注解则进行处理
        for(Field field : fields){
            boolean isAutowired =
                    field.isAnnotationPresent(Autowired.class);
            if(isAutowired){
                try {
                    //根据属性名查找同名的bean
                    String fieldName = field.getName();
                    //依据名称查找bean，即byName的形式
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    //设置属性值，完成注入
                    field.setAccessible(true);
                    field.set(bean, autowiredObj);
                    log.info("autowire {} for bean{}", fieldName, beanName);
                }catch (IllegalAccessException e){
                    log.error(e.getMessage());
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return null;
    }

}

