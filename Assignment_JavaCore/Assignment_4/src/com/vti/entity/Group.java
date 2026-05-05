package com.vti.entity;// com.vti.entity.Group.java

import java.util.Date;

public class Group {
    private int groupID;
    private String groupName;
    private Account creator;
    private Date createDate;
    private Account[] accounts;

    public Group() {
    }

    // Question 3 - b
    public Group(String groupName, Account creator, Account[] accounts, Date createDate) {
        this.groupName = groupName;
        this.creator = creator;
        this.accounts = accounts;
        this.createDate = createDate;
    }

    // Question 3 - c
    public Group(String groupName, Account creator, String[] usernames, Date createDate) {
        this.groupName = groupName;
        this.creator = creator;
        this.accounts = new Account[usernames != null ? usernames.length : 0];
        if (usernames != null) {
            for (int i = 0; i < usernames.length; i++) {
                Account acc = new Account();
                acc.setUsername(usernames[i]);
                this.accounts[i] = acc;
            }
        }
        this.createDate = createDate;
    }

    public Group(int groupID, String groupName, Account creator, Date createDate) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Account[] accounts) {
        this.accounts = accounts;
    }
}