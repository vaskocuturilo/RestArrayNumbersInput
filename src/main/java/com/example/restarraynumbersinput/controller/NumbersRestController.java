package com.example.restarraynumbersinput.controller;

import com.example.restarraynumbersinput.model.NumberModel;
import com.example.restarraynumbersinput.service.NumbersService;
import com.example.restarraynumbersinput.service.StorageFileService;
import com.example.restarraynumbersinput.service.UploadServiceImplementation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/numbers")
@Log4j2
@AllArgsConstructor
public class NumbersRestController {

    private final StorageFileService storageFileService;
    private final NumbersService numbersService;
    private final UploadServiceImplementation uploadServiceImplementation;

    @GetMapping("/all")
    public ResponseEntity handleGetAllNumbers() {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(numbersService.handleGetAllNumbers());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity handleDeleteAllNumbers() {
        try {
            numbersService.handleDeleteAllNumbers();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("All numbers was delete.");
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @GetMapping("/operations")
    public ResponseEntity handleGetOperationsNumber() {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(numbersService.handleGetOperationsNumber());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity handleCreateNumber(@RequestBody List<NumberModel> numberEntity, UriComponentsBuilder uriComponentsBuilder) {
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
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            storageFileService.deleteAll();
            storageFileService.init();
            storageFileService.saveFile(multipartFile);
            uploadServiceImplementation.uploadFile(multipartFile);
            String message = "Uploaded the file successfully: " + multipartFile.getOriginalFilename();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("File upload -> " + message);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("File didn't upload." + exception.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> getAllEmployeesInCsv() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "download_" + new Date().getTime() + ".csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(numbersService.writeEmployeesToCsv()));
    }
}
