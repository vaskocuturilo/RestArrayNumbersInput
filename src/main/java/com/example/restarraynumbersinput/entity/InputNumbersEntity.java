package com.example.restarraynumbersinput.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "input_numbers")
@Data
public class InputNumbersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
}
