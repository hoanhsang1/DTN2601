package com.vti.entity;

import java.sql.Date;

public class Account {

    private int accountId;
    private String email;
    private String username;
    private String fullname;
    private Department departmentId;
    private Position positionId;
    private Date createDate;

    public Account() {
    }

    public Account(int accountId, String email, String username,
                   String fullname, Department departmentId,
                   Position positionId, Date createDate) {

        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.createDate = createDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Department getDepartment() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public Position getPosition() {
        return positionId;
    }

    public void setPositionId(Position positionId) {
        this.positionId = positionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", departmentId=" + departmentId +
                ", positionId=" + positionId +
                ", createDate=" + createDate +
                '}';
    }
}