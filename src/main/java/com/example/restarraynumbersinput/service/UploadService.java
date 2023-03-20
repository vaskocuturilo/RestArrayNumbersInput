package com.example.restarraynumbersinput.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    void uploadFile(MultipartFile multipartFile);
}
