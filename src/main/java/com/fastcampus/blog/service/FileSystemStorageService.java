package com.fastcampus.blog.service;

import com.fastcampus.blog.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

@Service
public class FileSystemStorageService {

    public String save(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ApiException("file is empty", HttpStatus.BAD_REQUEST);
        }

        String pathString = "web/www/files/" + Instant.now().getEpochSecond() + "_" + file.getOriginalFilename();
        Path path = Paths.get(pathString);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return pathString;
    }
}
