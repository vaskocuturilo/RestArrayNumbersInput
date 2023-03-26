package com.example.restarraynumbersinput;

import com.example.restarraynumbersinput.service.StorageFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestArrayNumbersInputApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestArrayNumbersInputApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageFileService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
