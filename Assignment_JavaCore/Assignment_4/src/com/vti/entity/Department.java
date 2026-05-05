package com.vti.entity;

// com.vti.entity.Department.java
public class Department {
    private static int counter = 0; // đếm chung

    private int departmentID;       // id riêng
    private String departmentName;

    public Department() {
        this.departmentID = counter++;
    }

    public Department(String departmentName) {
        this.departmentID = counter++;
        this.departmentName = departmentName;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}