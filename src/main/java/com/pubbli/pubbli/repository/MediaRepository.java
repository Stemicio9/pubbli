package com.pubbli.pubbli.repository;

import com.pubbli.pubbli.model.Media;
import com.pubbli.pubbli.model.Sequenza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media,Long > {

    List<Media> findAllByIdSequenza(long id);

    void deleteAllByIdSequenza(long id);

}
