package com.example.restarraynumbersinput.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "input_numbers")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    public NumberEntity(Integer number) {
        this.number = number;
    }
}
