package com.example.restarraynumbersinput.service;

import com.example.restarraynumbersinput.entity.NumberEntity;
import com.example.restarraynumbersinput.model.NumberModel;
import com.example.restarraynumbersinput.repository.NumbersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.restarraynumbersinput.model.NumberModel.dtoToEntity;

@Service
@Log4j2
public class NumbersService {
    private final NumbersRepository numbersRepository;

    public NumbersService(NumbersRepository numbersRepository) {
        this.numbersRepository = numbersRepository;
    }

    public List<NumberEntity> handleGetAllNumbers() {
        return numbersRepository.findAll();
    }

    public Iterable<NumberEntity> handleCreateNumber(List<NumberModel> numberModels) {
        List<NumberEntity> numberEntities = new ArrayList<>();
        for (NumberModel numberModel : numberModels) {
            numberEntities.add(dtoToEntity(numberModel));
        }

        return numbersRepository.saveAll(numberEntities);
    }
}
