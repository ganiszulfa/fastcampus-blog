package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Media;
import com.fastcampus.blog.mapper.MediaMapper;
import com.fastcampus.blog.repository.MediaRepository;
import com.fastcampus.blog.response.media.CreateMediaResponse;
import com.fastcampus.blog.response.media.GetMediaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    FileSystemStorageService fileSystemStorageService;

    @Autowired
    GoogleCloudStorageService googleCloudStorageService;

    public List<GetMediaResponse> getMediaResponseList(Integer pageNo, Integer limit) {
        List<Media> mediaList = (List<Media>) mediaRepository.findAll();
        return MediaMapper.INSTANCE.mapToGetMediaResponse(mediaList);
    }

    public CreateMediaResponse createMedia(MultipartFile file, String name) {

        String path = googleCloudStorageService.save(file);
        Media media = new Media();
        media.setCreatedAt(Instant.now().getEpochSecond());
        media.setPath(path);
        media.setName(name);
        mediaRepository.save(media);

        return CreateMediaResponse.builder().path(path).build();
    }
}
