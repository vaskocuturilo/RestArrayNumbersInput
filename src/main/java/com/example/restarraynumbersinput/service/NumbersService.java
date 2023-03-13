package com.example.restarraynumbersinput.service;

import com.example.restarraynumbersinput.entity.NumberEntity;
import com.example.restarraynumbersinput.repository.NumbersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public NumberEntity handleCreateNumber(NumberEntity numberEntity) {
        if (numberEntity.getNumber() == null) {
            throw new IllegalStateException("Not correctly format");
        }
        return numbersRepository.save(numberEntity);
    }
}
