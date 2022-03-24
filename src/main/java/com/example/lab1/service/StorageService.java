package com.example.lab1.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public File uploadMultipartFile(MultipartFile file) {
        File convertedFile = convertMultipartFile(file);
        String filename = file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, filename, convertedFile));
        return convertedFile;
    }

    public void uploadFile(File file) {
        String filename = file.getName();
        s3Client.putObject(new PutObjectRequest(bucketName, filename, file));
    }

    public File downloadFile(String filename) {
        S3Object s3Object = s3Client.getObject(bucketName, filename);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            File file = new File(filename);
            Files.write(IOUtils.toByteArray(inputStream), file);
            return file;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new File(filename);
    }

    public String deleteFile(String file) {
        s3Client.deleteObject(bucketName, file);
        return file + " deleted";
    }

    public File convertMultipartFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
