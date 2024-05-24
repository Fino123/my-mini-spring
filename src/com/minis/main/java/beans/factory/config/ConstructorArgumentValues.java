package com.minis.main.java.beans.factory.config;

import java.util.*;

/**
 * @description: 管理一堆的ArgumentValue
 */
public class ConstructorArgumentValues {
    // 有序Argument
    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues = new HashMap<>(0);
    // 无序Argument
    private final List<ConstructorArgumentValue> genericArgumentValues = new LinkedList<>();

    public ConstructorArgumentValues() {
    }

    // 添加有序Argument
    private void addArgumentValue(Integer key, ConstructorArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type, String name) {
        this.genericArgumentValues.add(new ConstructorArgumentValue(value, type, name));
    }

    private void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ConstructorArgumentValue> it =
                 this.genericArgumentValues.iterator(); it.hasNext(); ) {
                ConstructorArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    it.remove();
                }
            }
        }
        this.genericArgumentValues.add(newValue);
    }

    public ConstructorArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructorArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }

    public ConstructorArgumentValue getIndexedGenericArgumentValue(int index) {
        return this.genericArgumentValues.get(index);
    }
}
