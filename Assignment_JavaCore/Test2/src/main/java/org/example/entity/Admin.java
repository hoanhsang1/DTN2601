package org.example.entity;

public class Admin extends User{
    private int expInYear;

    public Admin(String fullname, String email, String password,  int expInYear) {
        super(fullname, email, password);
        this.expInYear = expInYear;
    }

    public Admin(int id, String fullname, String email, String password, int expInYear) {
        super(id, fullname, email, password);
        this.expInYear = expInYear;
    }

    public int getExpInYear() {
        return expInYear;
    }


}
