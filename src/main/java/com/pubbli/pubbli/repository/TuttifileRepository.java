package com.pubbli.pubbli.repository;

import com.pubbli.pubbli.model.Tuttifile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuttifileRepository extends JpaRepository<Tuttifile, Long > {

    Tuttifile findByUrlTuttifile(String url);
}
