package com.fijalkowskim.travelmemories.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fijalkowskim.travelmemories.models.photos.Likes;

public interface LikesDAORepository extends JpaRepository<Likes, Long> {
    Page<Likes> findAllByPhotoId(Long photo_id, Pageable pageable);
    Likes findByPhotoIdAndUserId(Long photoId, Long userId);
}
