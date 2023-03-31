package com.example.restarraynumbersinput.service;

import com.example.restarraynumbersinput.helper.ExportHelper;
import com.example.restarraynumbersinput.entity.NumberEntity;
import com.example.restarraynumbersinput.model.NumberModel;
import com.example.restarraynumbersinput.repository.NumbersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
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
            throw new IllegalStateException("The list of numbers is Empty. Add List of numbers before, please.");
        }

        Integer smallestNumber = entityList.stream().mapToInt(NumberEntity::getNumber).min().orElseThrow(NoSuchElementException::new);
        Integer largestNumber = entityList.stream().mapToInt(NumberEntity::getNumber).max().orElseThrow(NoSuchElementException::new);
        Integer averageNumber = (int) entityList.stream().mapToInt(NumberEntity::getNumber).average().orElseThrow(NoSuchElementException::new);
        Integer medianNumber = getMedianNumber(entityList);
        Integer longestSequenceAsc = longestSequenceByAsc(entityList);
        Integer longestSequenceDesc = longestSequenceByDesc(entityList);

        return List.of(smallestNumber, largestNumber, averageNumber, medianNumber, longestSequenceAsc, longestSequenceDesc);
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

    public ByteArrayInputStream writeEmployeesToCsv() {
        List<NumberEntity> numberEntities = numbersRepository.findAll();

        return ExportHelper.exportDataFromDatabaseToCSV(numberEntities);
    }

    private static Integer getMedianNumber(List<NumberEntity> entityList) {
        DoubleStream doubleStream = entityList.stream().mapToDouble(NumberEntity::getNumber).sorted();

        double result = entityList.size() % 2 == 0 ?
                doubleStream.skip(entityList.size() / 2 - 1).limit(2).average().getAsDouble() :
                doubleStream.skip(entityList.size() / 2).findFirst().getAsDouble();

        return (int) result;
    }

    private static Integer longestSequenceByAsc(List<NumberEntity> entityList) {
        Integer[] array = entityList.stream()
                .map(NumberEntity::getNumber)
                .toArray(Integer[]::new);

        final HashSet<Integer> hashSet = new HashSet<>();
        for (int i : array) hashSet.add(i);

        int longestSequenceLen = 0;
        for (int i : array) {
            int length = 1;
            for (int j = i - 1; hashSet.contains(j); --j) {
                hashSet.remove(j);
                ++length;
            }
            for (int j = i + 1; hashSet.contains(j); ++j) {
                hashSet.remove(j);
                ++length;
            }
            longestSequenceLen = Math.max(longestSequenceLen, length);
        }
        return longestSequenceLen;
    }

    private static Integer longestSequenceByDesc(List<NumberEntity> entityList) {
        Integer[] array = entityList.stream()
                .map(NumberEntity::getNumber)
                .toArray(Integer[]::new);

        int downSequence = 1;
        int longestDownSequence = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] <= array[i - 1]) downSequence++;
            else {
                if (downSequence > longestDownSequence)
                    longestDownSequence = downSequence;
                downSequence = 1;
            }
        }
        if (downSequence > longestDownSequence)
            longestDownSequence = downSequence;
        return longestDownSequence;
    }
}
