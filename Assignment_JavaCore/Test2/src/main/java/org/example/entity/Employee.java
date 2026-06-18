package org.example.entity;

import org.example.enums.ProSkill;

public class Employee extends User{
    private ProSkill proSkill;

    public Employee(String fullname, String email, String password, ProSkill proSkill) {
        super(fullname, email, password);
        this.proSkill = proSkill;
    }

    public Employee(int id, String fullname, String email, String password, ProSkill proSkill) {
        super(id, fullname, email, password);
        this.proSkill = proSkill;
    }

    public ProSkill getProSkill() {
        return proSkill;
    }

    public void setProSkill(ProSkill proSkill) {
        this.proSkill = proSkill;
    }
}
