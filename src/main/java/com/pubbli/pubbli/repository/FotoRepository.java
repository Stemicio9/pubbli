package com.pubbli.pubbli.repository;

import com.pubbli.pubbli.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long > {

    Foto findByUrlFoto (String url);
}
