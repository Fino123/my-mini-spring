package com.minis.main.java.test;

import com.minis.main.java.beans.factory.annotation.Autowired;
import lombok.Data;

@Data
public class BaseService {
    @Autowired
    private BaseBaseService basebaseservice;

    public BaseService() {
    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        basebaseservice.sayHello();
    }
}
