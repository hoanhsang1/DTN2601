package com.vti.entity;

public class Manager extends User {

    public Manager() {
    }

    public Manager(String name, double salaryRatio) {
        super(name, salaryRatio);
    }

    @Override
    public double calculatePay() {
        return salaryRatio * 520;
    }
}