package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.requestmodels.LikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fijalkowskim.travelmemories.models.photos.Like;
import com.fijalkowskim.travelmemories.services.LikeService;

@RestController
@RequestMapping(value = "/api/likes")
@CrossOrigin("http://localhost:3000")
public class LikesController {
    private final LikeService likeService;

    @Autowired
    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/photo/{photoId}")
    public Page<Like> getLikesOfPhoto(
            @PathVariable long photoId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) throws CustomHTTPException {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return likeService.getLikesOfPhoto(photoId, pageRequest);
    }
    @GetMapping("/numberForPhoto/{photoId}")
    public int getNumberOfLikesForPhoto(
            @PathVariable long photoId) {
        return likeService.getNumberOfLikesForPhoto(photoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLike(
            @PathVariable long id) throws CustomHTTPException {
        return ResponseEntity.ok(likeService.getLikeById(id));
    }
    @GetMapping("/didLike")
    public ResponseEntity<Boolean> didUserLikePhoto(@RequestBody LikeRequest likeRequest) throws CustomHTTPException {
        return ResponseEntity.ok(likeService.didUserLikePhoto(likeRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable long id) throws CustomHTTPException {
        likeService.deleteLike(id);
        return ResponseEntity.ok("Like deleted successfully.");
    }
    @PutMapping("")
    public ResponseEntity<Like> addLike(@RequestBody LikeRequest likeRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.addLike(likeRequest));
    }
    @PostMapping("/likeDislike")
    public ResponseEntity<Like> likeDislikePhoto(@RequestBody LikeRequest likeRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.OK).body(likeService.userLikeDislikePhoto(likeRequest));
    }
}
