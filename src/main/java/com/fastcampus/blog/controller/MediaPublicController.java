package com.fastcampus.blog.controller;

import com.fastcampus.blog.response.media.CreateMediaResponse;
import com.fastcampus.blog.response.media.GetMediaResponse;
import com.fastcampus.blog.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/media")
public class MediaPublicController {

    @Autowired
    MediaService mediaService;

    @GetMapping
    public ResponseEntity<List<GetMediaResponse>> getMedia(
        @RequestParam(required = false, defaultValue = "0") Integer pageNo,
        @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(mediaService.getMediaResponseList(pageNo, limit));
    }

    @PostMapping
    public ResponseEntity<CreateMediaResponse> createMedia(@RequestParam("file") MultipartFile file,
                                                           String name) {
        return ResponseEntity.ok(mediaService.createMedia(file, name));
    }
}
