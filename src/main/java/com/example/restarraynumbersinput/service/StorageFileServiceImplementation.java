package com.example.restarraynumbersinput.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageFileServiceImplementation implements StorageFileService {
    private final Path folder = Paths.get("upload-folder");

    @Override
    public void init() {
        try {
            Files.createDirectory(folder);
        } catch (IOException exception) {
            throw new RuntimeException("Can't create folder for upload file." + exception.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        Path file = folder.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Can't read from file.");
            }
            return resource;
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void saveFile(MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(), this.folder.resolve(multipartFile.getOriginalFilename()));
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(folder);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
