package com.fijalkowskim.travelmemories.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fijalkowskim.travelmemories.models.photos.Photo;

public interface PhotoDAORepository extends JpaRepository<Photo, Long> {
    Page<Photo> findAllByPrivacyOrderByDateAsc(Long privacy, Pageable pageable);
    Page<Photo> findAllByPrivacyOrderByDateDesc(Long privacy, Pageable pageable);
    Page<Photo> findAllByStageIdAndPrivacyOrderByDateAsc(Long stage_id, Long privacy, Pageable pageable);
    Page<Photo> findAllByStageIdAndPrivacyOrderByDateDesc(Long stage_id, Long privacy, Pageable pageable);
    Page<Photo> findAllById(Long stage_id, Pageable pageable);
    Page<Photo> findAllByStageIdOrderByDateAsc(long stageId, Pageable pageable);
    Page<Photo> findAllByStageIdOrderByDateDesc(long stageId, Pageable pageable);
}
