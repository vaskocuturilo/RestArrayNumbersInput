package com.example.restarraynumbersinput.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "input_numbers")
@Data
public class NumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
}
