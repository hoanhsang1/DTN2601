package org.example.dto.csv;

import org.example.entity.Department;
import org.example.entity.Position;

import java.util.Date;

public class AccountCsv {
    private String username;
    private String fullName;
    private String email;
    private Department departmentID;
    private Position positionID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Department departmentID) {
        this.departmentID = departmentID;
    }

    public Position getPositionID() {
        return positionID;
    }

    public void setPositionID(Position positionID) {
        this.positionID = positionID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountCsv(String username, String fullName, String email, Department departmentID, Position positionID, Date createDate, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.departmentID = departmentID;
        this.positionID = positionID;
        this.createDate = createDate;
        this.password = password;
    }

    public AccountCsv() {
    }

    private Date createDate;
    private String password;
}
