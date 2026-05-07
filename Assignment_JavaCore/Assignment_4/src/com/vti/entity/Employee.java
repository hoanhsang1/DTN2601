package com.vti.entity;

public class Employee extends User {

    public Employee() {
    }

    public Employee(String name, double salaryRatio) {
        super(name, salaryRatio);
    }

    @Override
    public double calculatePay() {
        return salaryRatio * 420;
    }
}