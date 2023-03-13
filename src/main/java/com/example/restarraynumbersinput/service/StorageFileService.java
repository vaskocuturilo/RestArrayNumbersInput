package com.example.restarraynumbersinput.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageFileService {
    public void init();

    public Resource load(final String filename);

    public void saveFile(MultipartFile multipartFile);

    public void deleteAll();
}
