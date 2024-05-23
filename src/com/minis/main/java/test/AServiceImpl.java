package com.minis.main.java.test;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AServiceImpl implements AService {
    private String name;
    private int level;
    private String property1;
    private String property2;
    private BaseService ref1;

    public AServiceImpl(String name, int level){
        this.name = name;
        this.level = level;
    }

    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}
