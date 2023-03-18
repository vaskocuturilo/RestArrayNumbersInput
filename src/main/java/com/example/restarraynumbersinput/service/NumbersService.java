package com.example.restarraynumbersinput.service;

import com.example.restarraynumbersinput.entity.NumberEntity;
import com.example.restarraynumbersinput.model.NumberModel;
import com.example.restarraynumbersinput.repository.NumbersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;

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

    public List<Integer> handleGetOperationsNumber() {
        List<NumberEntity> entityList = numbersRepository.findAll();
        if (entityList.isEmpty()) {
            throw new RuntimeException("The list of numbers is Empty. Add List of numbers before, please.");
        }

        Integer smallestNumber = entityList.stream().mapToInt(version -> version.getNumber()).min().orElseThrow(NoSuchElementException::new);
        Integer largestNumber = entityList.stream().mapToInt(version -> version.getNumber()).max().orElseThrow(NoSuchElementException::new);
        Integer averageNumber = (int) entityList.stream().mapToInt(version -> version.getNumber()).average().orElseThrow(NoSuchElementException::new);
        Integer medianNumber = getMedianNumber(entityList);

        return List.of(smallestNumber, largestNumber, averageNumber, medianNumber);
    }

    public Iterable<NumberEntity> handleCreateNumber(List<NumberModel> numberModels) {
        for (NumberModel model : numberModels) {
            if (model.getNumber() == null) {
                throw new IllegalStateException("Not correctly format");
            }
        }
        List<NumberEntity> numberEntities = new ArrayList<>();
        for (NumberModel numberModel : numberModels) {
            numberEntities.add(dtoToEntity(numberModel));
        }

        return numbersRepository.saveAll(numberEntities);
    }

    public void handleDeleteAllNumbers() {
        numbersRepository.deleteAll();
    }

    private static Integer getMedianNumber(List<NumberEntity> entityList) {
        DoubleStream doubleStream = entityList.stream().mapToDouble(NumberEntity::getNumber).sorted();

        double result = entityList.size() % 2 == 0 ?
                doubleStream.skip(entityList.size() / 2 - 1).limit(2).average().getAsDouble() :
                doubleStream.skip(entityList.size() / 2).findFirst().getAsDouble();

        return (int) result;
    }
}
