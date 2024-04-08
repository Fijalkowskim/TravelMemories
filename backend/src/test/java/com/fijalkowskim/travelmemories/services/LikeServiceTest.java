package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomAccessDeniedHandler;
import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
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
    public void testGetLikesOfPhoto_Successfully(){
        PageRequest pageRequest = PageRequest.of(1, 1);
        when(likesDAORepository.findAllByPhotoId(1L, pageRequest))
                .thenReturn(Page.empty());
        assertThat(likeService.getLikesOfPhoto(1L, pageRequest)).isEqualTo(Page.empty());
    }
    @Test
    public void testGetLikesOfPhoto_NegativePhotoId(){
        PageRequest pageRequest = PageRequest.of(1, 1);
        assertThatThrownBy(()->likeService.getLikesOfPhoto(-1L, pageRequest)).isInstanceOf(CustomHTTPException.class);

    }
}
