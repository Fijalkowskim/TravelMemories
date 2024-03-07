package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.photos.Like;
import com.fijalkowskim.travelmemories.models.photos.Photo;
import com.fijalkowskim.travelmemories.models.stages.Stage;
import com.fijalkowskim.travelmemories.repositories.PhotoDAORepository;
import com.fijalkowskim.travelmemories.repositories.StageDAORepository;
import com.fijalkowskim.travelmemories.requestmodels.PhotoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@Transactional
@Service
public class PhotoService {
    private final PhotoDAORepository photoDAORepository;
    private final StageDAORepository stageDAORepository;
    @Autowired
    public PhotoService(PhotoDAORepository photoDAORepository,StageDAORepository stageDAORepository) {
        this.photoDAORepository = photoDAORepository;
        this.stageDAORepository = stageDAORepository;
    }
    public Page<Photo> getPhotosOfStage(long stageId, Pageable pageable, String sort){
        Page<Photo> photos;
        switch(sort.toLowerCase()){
            case "asc":
                photos = photoDAORepository.findAllByStageIdOrderByDateAsc(stageId, pageable);
                break;
            default:
                photos = photoDAORepository.findAllByStageIdOrderByDateDesc(stageId, pageable);
        }
        return photos;
    }
    public Photo getPhotoById(Long photoId) throws CustomHTTPException {
        Optional<Photo> photo = photoDAORepository.findById(photoId);
        if(photo.isEmpty()) throw new CustomHTTPException("Photo not found", HttpStatus.NOT_FOUND);
        return photo.get();
    }
    public Photo addPhoto(PhotoRequest photoRequest, MultipartFile multipartFile) throws CustomHTTPException {
        Photo photo = photoFromRequest(photoRequest);
        try{
            photo.setPhotoData(multipartFile.getBytes());
        }catch(IOException ex){
            throw new CustomHTTPException("Cannot use file.", HttpStatus.BAD_REQUEST);
        }
        return photoDAORepository.save(photo);
    }
    public Photo updatePhoto(long id, PhotoRequest photoRequest) throws CustomHTTPException {
        Optional<Photo> oldPhoto = photoDAORepository.findById(id);
        if(oldPhoto.isEmpty()) throw new CustomHTTPException("Photo not found", HttpStatus.NOT_FOUND);
        if(oldPhoto.get().getStage().getId() != photoRequest.getStageId()) throw new CustomHTTPException("Given parent stage is incorrect", HttpStatus.BAD_REQUEST);
        Photo photo = photoFromRequest(photoRequest);
        photo.setLikes(oldPhoto.get().getLikes());
        photo.setId(id);
        photo.setPhotoData(oldPhoto.get().getPhotoData());
        return photoDAORepository.save(photo);
    }
    public void deletePhoto(Long id) throws CustomHTTPException {
        Optional<Photo> photo = photoDAORepository.findById(id);
        if(photo.isEmpty()) throw new CustomHTTPException("Photo not found", HttpStatus.NOT_FOUND);
        photoDAORepository.delete(photo.get());
    }
    Photo photoFromRequest(PhotoRequest photoRequest) throws CustomHTTPException{
        Optional<Stage> stage = stageDAORepository.findById(photoRequest.getStageId());
        if(stage.isEmpty()) throw new CustomHTTPException("Stage not found", HttpStatus.NOT_FOUND);
        Photo photo = new Photo();
        photo.setStage(stage.get());
        photo.setDate(photoRequest.getDate());
        photo.setDescription(photoRequest.getDescription());
        photo.setLatitude(photoRequest.getLatitude());
        photo.setLongitude(photoRequest.getLongitude());
        photo.setLocationName(photoRequest.getLocationName());
        photo.setLikes(new HashSet<Like>());
        return photo;
    }

}
