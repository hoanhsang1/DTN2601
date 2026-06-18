package com.vti.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="department")
@Data
public class Department {
    @Id
    @Column(name="department_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="department_name",nullable=false,unique=true,length=100)
    private String name;

    @OneToMany(mappedBy = "accounts")
    private List<Account> Accounts;
}
