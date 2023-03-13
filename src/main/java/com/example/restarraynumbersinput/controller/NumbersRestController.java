package com.example.restarraynumbersinput.controller;

import com.example.restarraynumbersinput.entity.NumberEntity;
import com.example.restarraynumbersinput.service.NumbersService;
import com.example.restarraynumbersinput.service.StorageFileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/numbers")
@Log4j2
public class NumbersRestController {

    private final StorageFileService storageFileService;
    private final NumbersService numbersService;

    public NumbersRestController(StorageFileService storageFileService, NumbersService numbersService) {
        this.storageFileService = storageFileService;
        this.numbersService = numbersService;
    }

    @GetMapping("/all")
    public ResponseEntity handleGetAllNumbers() {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(numbersService.handleGetAllNumbers());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity handleCreateNumber(@RequestBody NumberEntity numberEntity, UriComponentsBuilder uriComponentsBuilder) {
        try {
            return ResponseEntity.created(uriComponentsBuilder
                            .path("/api/v1/numbers")
                            .build()
                            .toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(numbersService.handleCreateNumber(numberEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            storageFileService.init();
            storageFileService.saveFile(multipartFile);
            String message = "Uploaded the file successfully: " + multipartFile.getOriginalFilename();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("File upload -> " + message);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("File didn't upload.");
        }
    }
}
