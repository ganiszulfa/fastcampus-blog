package com.fastcampus.blog.service;

import com.fastcampus.blog.exception.ApiException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
public class GoogleCloudStorageService {

    public String save(MultipartFile file) {

        String pathString = Instant.now().getEpochSecond() + "_" + file.getOriginalFilename();
        Storage storage = StorageOptions.newBuilder().setProjectId("blog-project-434808").build().getService();
        BlobId blobId = BlobId.of("cdn.blog.ganis.net", pathString);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        try {
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return pathString;

    }
}
