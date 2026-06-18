package com.vti.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "group_account")
@Data
public class GroupAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "join_date")
    private LocalDate joinDate;
}
