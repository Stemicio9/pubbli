package com.pubbli.pubbli.repository;

import com.pubbli.pubbli.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long > {

    Video findByIdVideo (long idVideo);
}
