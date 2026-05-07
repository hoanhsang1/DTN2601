package com.vti.entity;

import java.util.Scanner;

public abstract class User {

    protected String name;
    protected double salaryRatio;

    public User() {
    }

    public User(String name, double salaryRatio) {
        this.name = name;
        this.salaryRatio = salaryRatio;
    }

    public void inputInfo() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập tên: ");
        name = sc.nextLine();

        System.out.print("Nhập salary ratio: ");
        salaryRatio = Double.parseDouble(sc.nextLine());
    }

    public String getName() {
        return name;
    }

    public double getSalaryRatio() {
        return salaryRatio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalaryRatio(double salaryRatio) {
        this.salaryRatio = salaryRatio;
    }

    public abstract double calculatePay();

    public void displayInfor() {
        System.out.println("Tên: " + name);
        System.out.println("Salary Ratio: " + salaryRatio);
        System.out.println("Thu nhập: " + calculatePay());
    }
}