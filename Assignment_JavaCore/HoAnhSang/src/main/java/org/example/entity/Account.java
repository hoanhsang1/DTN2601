package org.example.entity;

public class Account {
    private int accountId;
    private String accountEmail;
    private String accountPassword;
    private String accountFullName;

    public int getAccountId() {
        return accountId;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountFullName() {
        return accountFullName;
    }

    public void setAccountFullName(String accountFullName) {
        this.accountFullName = accountFullName;
    }

    public Account(int accountId, String accountEmail, String accountPassword, String accountFullName) {
        this.accountId = accountId;
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
        this.accountFullName = accountFullName;
    }
}
