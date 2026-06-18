package com.vti.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "fullname", nullable = false, length = 100)
    private String fullName;

    @Column(name = "department_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentId;

    @Column(name = "position_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position positionId;

    @Column(name = "create_date")
    private LocalDate createDate;
}
