package com.vti.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "`group`")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;

    @Column(name = "group_name", length = 100, nullable = false)
    private String name;

    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;

    @Column(name = "create_date")
    private LocalDate createDate;
}
