package org.example.dto.csv;

public class DepartmentCsv {
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public DepartmentCsv(String departmentName) {
        this.departmentName = departmentName;
    }

    public DepartmentCsv() {
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
