package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.requestmodels.PhotoRequest;
import com.fijalkowskim.travelmemories.requestmodels.StageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fijalkowskim.travelmemories.models.photos.Photo;
import com.fijalkowskim.travelmemories.models.stages.Stage;
import com.fijalkowskim.travelmemories.services.PhotoService;
import com.fijalkowskim.travelmemories.services.StageService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/photos")
@CrossOrigin("http://localhost:3000")
public class PhotoController {
    PhotoService photoService;
    @Autowired
    public PhotoController(PhotoService photoService){
        this.photoService = photoService;
    }

    @GetMapping("/stage/{stageId}")
    public Page<Photo> getPhotosOfStage(
            @PathVariable long stageId,
            @RequestParam(name = "sort", defaultValue = "") String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) throws CustomHTTPException {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return photoService.getPhotosOfStage(stageId, pageRequest, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhoto(
            @PathVariable long id) throws CustomHTTPException {
        return ResponseEntity.ok(photoService.getPhotoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable long id) throws CustomHTTPException {
        photoService.deletePhoto(id);
        return ResponseEntity.ok("Photo deleted successfully.");
    }
    @PutMapping("")
    public ResponseEntity<Photo> addPhoto(
            @RequestPart("photoRequest") PhotoRequest photoRequest,
            @RequestPart("photoData") MultipartFile photoData) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.CREATED).body(photoService.addPhoto(photoRequest,photoData));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable long id, @RequestBody PhotoRequest photoRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.OK).body(photoService.updatePhoto(id,photoRequest));
    }

}
