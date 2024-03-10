package com.fijalkowskim.travelmemories.repositories;

import com.fijalkowskim.travelmemories.models.stages.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fijalkowskim.travelmemories.models.photos.Like;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikesDAORepository extends JpaRepository<Like, Long> {
    Page<Like> findAllByPhotoId(Long photo_id, Pageable pageable);
    Optional<Like> findByPhotoIdAndUserId(Long photo_id, Long user_id);
    @Query("SELECT COUNT(l) FROM Like l WHERE l.photo.id = :photo_id")
    int getNumberOfLikesForPhoto(@Param("photo_id") Long photo_id);
}
