package com.example.restarraynumbersinput.service;

import com.example.restarraynumbersinput.model.NumberModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class UploadServiceImplementation implements UploadService {
    private final NumbersService numbersService;

    private final static String PATH = "upload-folder/";

    @Override
    public void uploadFile(MultipartFile multipartFile) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<NumberModel>> typeReference = new TypeReference<List<NumberModel>>() {
        };
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(PATH + multipartFile.getOriginalFilename());
        } catch (FileNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Log Message -> : Class UploadServiceImplementation, method upload. File not found.", e.getMessage());
            }
            throw new RuntimeException(e);
        }
        try {
            List<NumberModel> numberEntities = mapper.readValue(inputStream, typeReference);
            numbersService.handleCreateNumber(numberEntities);
            log.info(multipartFile.getOriginalFilename() + " was save to upload-dir ");
        } catch (IOException exception) {
            if (log.isDebugEnabled()) {
                log.debug("Log Message -> : Class UploadServiceImplementation, method upload is down.", exception.getMessage());
            }
            throw new RuntimeException(exception.getMessage());
        }
    }
}
