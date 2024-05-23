package com.minis.main.java.test;


import lombok.Data;

@Data
public class BaseBaseService {
    private AServiceImpl as;

    public BaseBaseService() {

    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");
    }
}
