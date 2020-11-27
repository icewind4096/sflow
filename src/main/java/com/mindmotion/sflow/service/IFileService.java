package com.mindmotion.sflow.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String saveUploadFileToWebServer(MultipartFile file, String path);
}
