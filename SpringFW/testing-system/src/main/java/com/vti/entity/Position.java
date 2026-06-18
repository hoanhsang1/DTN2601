package com.vti.entity;

import com.vti.enums.ArticlePositionNameConverter;
import com.vti.enums.PositionName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "position")
@Data
public class Position {
    @Id
    @Column(name = "position_id")
    private Integer id;

    @Column(name = "position_name", nullable = false)
    private PositionName name;
}
