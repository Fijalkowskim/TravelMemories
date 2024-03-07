package com.fijalkowskim.travelmemories.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fijalkowskim.travelmemories.models.photos.Like;

public interface LikesDAORepository extends JpaRepository<Like, Long> {
    Page<Like> findAllByPhotoId(Long photo_id, Pageable pageable);
    Like findByPhotoIdAndUserId(Long photoId, Long userId);
}
