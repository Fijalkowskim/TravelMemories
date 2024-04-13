package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomAccessDeniedHandler;
import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.photos.Like;
import com.fijalkowskim.travelmemories.repositories.LikesDAORepository;
import com.fijalkowskim.travelmemories.repositories.PhotoDAORepository;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @InjectMocks
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
    public void GetLikeById_ProperData_Success(){
        final long id = 1;
        final Optional<Like> expectedLike = Optional.of(Like.builder().id(id).build());
        when(likesDAORepository.findById(id)).thenReturn(expectedLike);

        Like returnedLike = likeService.getLikeById(id);

        assertThat(returnedLike).isEqualTo(expectedLike.get());
    }
    @Test
    public void GetLikeById_NoSuchLike_ExceptionThrown(){
        final long id = 2;
        final Optional<Like> expectedLike = Optional.empty();
        when(likesDAORepository.findById(id)).thenReturn(expectedLike);

        assertThatThrownBy(()->likeService.getLikeById(id))
                .isInstanceOf(CustomHTTPException.class);
    }
}
