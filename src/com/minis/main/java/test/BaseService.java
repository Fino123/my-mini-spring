package com.minis.main.java.test;

import lombok.Data;

@Data
public class BaseService {
    private BaseBaseService bbs;

    public BaseService() {
    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        bbs.sayHello();
    }
}
