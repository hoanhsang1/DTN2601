package com.vti.entity;// com.vti.entity.Account.java

import java.util.Date;

public class Account {
    private String id;
    private String name;
    private int balance;

    public Account(String id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // getter
    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }


    public void credit(int amount) {
        this.balance += amount;
    }

    public void debit(int amount) {
        if (amount <= balance) {
            balance -= amount;
        }else  {
            System.out.println("Bạn k có đủ tiền");
        }
    }

    public void transferTo(Account account, int amount) {
        this.debit(amount);
        account.credit(amount);
    }
}