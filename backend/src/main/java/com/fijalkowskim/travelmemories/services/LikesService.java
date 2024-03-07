package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.models.photos.Photo;
import com.fijalkowskim.travelmemories.repositories.LikesDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.models.photos.Like;

import java.util.Optional;

@Transactional
@Service
public class LikesService {
    private final LikesDAORepository likesDAORepository;
    private PhotoService photoService;

    @Autowired
    public LikesService(LikesDAORepository likesDAORepository, PhotoService photoService) {
        this.likesDAORepository = likesDAORepository;
        this.photoService = photoService;
    }

    public boolean save(Long photoId,Long userId){
//        Like foundLike = likesDAORepository.findByPhotoIdAndUserId(photoId, userId);
//        if(foundLike == null){
//            Like like = new Like();
//            Optional<Photo> photo = photoService.getById(photoId);
//            if(photo.isEmpty()) throw new RuntimeException();
//            like.setPhoto(photo.get());
//            like.setUserId(userId);
//            likesDAORepository.save(like);
//            return true;
//        }
//        likesDAORepository.delete(foundLike);
        return false;
    }

    public Page<Like> getAllLikesByPhotoId(String photoId, Pageable pageable){
            return likesDAORepository.findAllByPhotoId(Long.valueOf(photoId), pageable);

    }

}
