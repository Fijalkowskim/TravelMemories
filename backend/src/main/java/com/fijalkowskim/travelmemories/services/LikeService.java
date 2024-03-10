package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.photos.Photo;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.repositories.LikesDAORepository;
import com.fijalkowskim.travelmemories.repositories.PhotoDAORepository;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import com.fijalkowskim.travelmemories.requestmodels.LikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.models.photos.Like;

import java.util.Optional;

@Transactional
@Service
public class LikeService {
    private final LikesDAORepository likeDAORepository;
    private final PhotoDAORepository photoDAORepository;
    private final UserDAORepository userDAORepository;

    @Autowired
    public LikeService(LikesDAORepository likeDAORepository, PhotoDAORepository photoDAORepository, UserDAORepository userDAORepository) {
        this.likeDAORepository = likeDAORepository;
        this.photoDAORepository = photoDAORepository;
        this.userDAORepository = userDAORepository;
    }

    public Page<Like> getLikesOfPhoto(long photoId, Pageable pageable){
        return likeDAORepository.findAllByPhotoId(photoId,pageable);
    }
    public Like getLikeById(Long likeId) throws CustomHTTPException {
        Optional<Like> like = likeDAORepository.findById(likeId);
        if (like.isEmpty()) throw new CustomHTTPException("Like not found", HttpStatus.NOT_FOUND);
        return like.get();
    }
    public Like addLike(LikeRequest likeRequest) throws CustomHTTPException {
        if(likeDAORepository.findByPhotoIdAndUserId(likeRequest.getPhotoId(), likeRequest.getUserId()).isPresent())
            throw new CustomHTTPException("User already liked this photo", HttpStatus.BAD_REQUEST);
        Like like = likeFromRequest(likeRequest);
        return likeDAORepository.save(like);
    }
    public void deleteLike(Long id) throws CustomHTTPException {
        Optional<Like> like = likeDAORepository.findById(id);
        if(like.isEmpty()) throw new CustomHTTPException("Like not found", HttpStatus.NOT_FOUND);
        likeDAORepository.delete(like.get());
    }
    public int getNumberOfLikesForPhoto(long photoId){
        return likeDAORepository.getNumberOfLikesForPhoto(photoId);
    }
    public Like userLikeDislikePhoto(LikeRequest likeRequest) throws CustomHTTPException{
        Optional<Like> oldLike = likeDAORepository.findByPhotoIdAndUserId(likeRequest.getPhotoId(), likeRequest.getUserId());
        if(oldLike.isPresent()){
            likeDAORepository.delete(oldLike.get());
            return null;
        }
        Like like = likeFromRequest(likeRequest);
        return likeDAORepository.save(like);
    }
    public boolean didUserLikePhoto(LikeRequest likeRequest) throws CustomHTTPException{
        return likeDAORepository.findByPhotoIdAndUserId(likeRequest.getPhotoId(), likeRequest.getUserId()).isPresent();
    }
    Like likeFromRequest(LikeRequest likeRequest) throws CustomHTTPException{
        Like like = new Like();
        Optional<User> user = userDAORepository.findById(likeRequest.getUserId());
        if(user.isEmpty()) throw new CustomHTTPException("User not found", HttpStatus.NOT_FOUND);
        Optional<Photo> photo = photoDAORepository.findById(likeRequest.getPhotoId());
        if(photo.isEmpty()) throw new CustomHTTPException("Photo not found", HttpStatus.NOT_FOUND);
        like.setUser(user.get());
        like.setPhoto(photo.get());
        return like;
    }

}
