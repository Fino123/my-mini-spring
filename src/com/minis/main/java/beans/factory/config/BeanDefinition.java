package com.minis.main.java.beans.factory.config;

import com.minis.main.java.beans.ArgumentValues;
import com.minis.main.java.beans.PropertyValues;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    private boolean lazyInit = false;
    private String[] dependsOn;
    private ArgumentValues constructorArgumentValues;
    private PropertyValues propertyValues;
    private String initMethodName;
    private volatile Object beanClass;
    private String id;
    private String className;
    private String scope = SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return scope.equals(SCOPE_PROTOTYPE);
    }

    public boolean isPrototype() {
        return scope.equals(SCOPE_PROTOTYPE);
    }

    public boolean isLazyInit() {
        return lazyInit;
    }
}
