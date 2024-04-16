package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomAccessDeniedHandler;
import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.photos.Like;
import com.fijalkowskim.travelmemories.repositories.LikesDAORepository;
import com.fijalkowskim.travelmemories.repositories.PhotoDAORepository;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import com.fijalkowskim.travelmemories.requestmodels.LikeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @InjectMocks
    @Spy
    private LikeService likeService;
    @Mock
    private UserDAORepository userDAORepository;
    @Mock
    private LikesDAORepository likesDAORepository;
    @Mock
    private PhotoDAORepository photoDAORepository;

    @Test
    public void GetLikesOfPhoto_ProperData_Success(){
        PageRequest pageRequest = PageRequest.of(1, 1);
        when(likesDAORepository.findAllByPhotoId(1L, pageRequest))
                .thenReturn(Page.empty());

        Page<Like> page = likeService.getLikesOfPhoto(1L, pageRequest);

        assertThat(page).isEqualTo(Page.empty());
    }
    @Test
    public void GetLikesOfPhoto_NegativePhotoId_ExceptionThrown(){
        PageRequest pageRequest = PageRequest.of(1, 1);

        assertThatThrownBy(()->likeService.getLikesOfPhoto(-1L, pageRequest))
                .isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void GetLikeById_ProperId_Success(){
        long id = 1;
        Optional<Like> expectedLike = Optional.of(Like.builder().id(id).build());
        when(likesDAORepository.findById(id)).thenReturn(expectedLike);

        Like returnedLike = likeService.getLikeById(id);

        assertThat(returnedLike).isEqualTo(expectedLike.get());
    }
    @Test
    public void GetLikeById_NoSuchLike_ExceptionThrown(){
        long id = 2;
        Optional<Like> expectedLike = Optional.empty();
        when(likesDAORepository.findById(id)).thenReturn(expectedLike);

        assertThatThrownBy(()->likeService.getLikeById(id))
                .isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void GetLikeById_NegativeId_ExceptionThrown(){
        long id = -1;
        Optional<Like> expectedLike = Optional.empty();
        when(likesDAORepository.findById(id)).thenReturn(expectedLike);

        assertThatThrownBy(()->likeService.getLikeById(id))
                .isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void AddLike_ProperData_Success(){
        long photoId = 1;
        long userId = 1;
        LikeRequest likeRequest = LikeRequest.builder().photoId(photoId).userId(userId).build();
        Like expectedLike = Like.builder().id(1L).build();
        when(likesDAORepository.findByPhotoIdAndUserId(photoId,userId)).thenReturn(Optional.empty());
        when(likesDAORepository.save(expectedLike)).thenReturn(expectedLike);
        doReturn(expectedLike).when(likeService).likeFromRequest(likeRequest);

        Like createdLike = likeService.addLike(likeRequest);

        assertThat(createdLike).isEqualTo(expectedLike);
    }
    @Test
    public void AddLike_ExistingLike_ExceptionThrown(){
        long photoId = 2;
        long userId = 1;
        LikeRequest likeRequest = LikeRequest.builder().photoId(photoId).userId(userId).build();
        Like expectedLike = Like.builder().id(1L).build();
        when(likesDAORepository.findByPhotoIdAndUserId(photoId,userId)).thenReturn(Optional.of(expectedLike));

        assertThatThrownBy(() -> likeService.addLike(likeRequest)).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void AddLike_InvalidUserId_ExceptionThrown(){
        long photoId = 1;
        long userId = -1;
        LikeRequest likeRequest = LikeRequest.builder().photoId(photoId).userId(userId).build();
        when(likesDAORepository.findByPhotoIdAndUserId(photoId,userId)).thenReturn(Optional.empty());
        doThrow(new CustomHTTPException("No such user", HttpStatus.BAD_REQUEST)).when(likeService).likeFromRequest(likeRequest);

        assertThatThrownBy(() -> likeService.addLike(likeRequest)).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void AddLike_InvalidPhotoId_ExceptionThrown(){
        long photoId = -1;
        long userId = 1;
        LikeRequest likeRequest = LikeRequest.builder().photoId(photoId).userId(userId).build();
        when(likesDAORepository.findByPhotoIdAndUserId(photoId,userId)).thenReturn(Optional.empty());
        doThrow(new CustomHTTPException("No such photo", HttpStatus.BAD_REQUEST)).when(likeService).likeFromRequest(likeRequest);

        assertThatThrownBy(() -> likeService.addLike(likeRequest)).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void DeleteLike_ProperId_Success(){
        long id = 1;
        Optional<Like> expectedOptional = Optional.of(Like.builder().id(id).build());
        when(likesDAORepository.findById(id)).thenReturn(expectedOptional);

        likeService.deleteLike(id);

        verify(likesDAORepository).delete(expectedOptional.get());
    }
    @Test
    public void DeleteLike_NoSuchLike_ExceptionThrown(){
        long id = 2;
        Optional<Like> expectedOptional = Optional.empty();
        when(likesDAORepository.findById(id)).thenReturn(expectedOptional);

        assertThatThrownBy(() -> likeService.deleteLike(id)).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void DeleteLike_NegativeId_ExceptionThrown(){
        long id = -1;
        Optional<Like> expectedOptional = Optional.empty();
        when(likesDAORepository.findById(id)).thenReturn(expectedOptional);

        assertThatThrownBy(() -> likeService.deleteLike(id)).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void GetNumberOfLikesForPhoto_ProperId_Success(){
        long id = 1;
        int expectedNumberOfLikes = 1;
        when(likesDAORepository.getNumberOfLikesForPhoto(id)).thenReturn(expectedNumberOfLikes);

        int returnedNumberOfLikes = likeService.getNumberOfLikesForPhoto(id);

        assertThat(returnedNumberOfLikes).isEqualTo(expectedNumberOfLikes);
    }
    @Test
    public void GetNumberOfLikesForPhoto_NoSuchLike_Zero(){
        long id = 2;
        int expectedNumberOfLikes = 0;
        when(likesDAORepository.getNumberOfLikesForPhoto(id)).thenReturn(expectedNumberOfLikes);

        int returnedNumberOfLikes = likeService.getNumberOfLikesForPhoto(id);

        assertThat(returnedNumberOfLikes).isEqualTo(expectedNumberOfLikes);
    }
    @Test
    public void GetNumberOfLikesForPhoto_NegativeId_Zero(){
        long id = -1;
        int expectedNumberOfLikes = 0;
        when(likesDAORepository.getNumberOfLikesForPhoto(id)).thenReturn(expectedNumberOfLikes);

        int returnedNumberOfLikes = likeService.getNumberOfLikesForPhoto(id);

        assertThat(returnedNumberOfLikes).isEqualTo(expectedNumberOfLikes);
    }
}
