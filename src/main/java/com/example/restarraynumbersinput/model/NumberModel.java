package com.example.restarraynumbersinput.model;

import com.example.restarraynumbersinput.entity.NumberEntity;
import lombok.Data;

@Data
public class NumberModel {
    private Long id;
    private Integer number;
    public static NumberEntity dtoToEntity(NumberModel numberModel) {
        NumberEntity numberEntity = new NumberEntity();
        numberEntity.setId(numberModel.getId());
        numberEntity.setNumber(numberModel.getNumber());

        return numberEntity;
    }
}
