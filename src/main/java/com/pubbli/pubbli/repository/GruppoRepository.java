package com.pubbli.pubbli.repository;

import com.pubbli.pubbli.model.Dispositivo;
import com.pubbli.pubbli.model.Gruppo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GruppoRepository  extends JpaRepository<Gruppo, Long > {

        Gruppo findByIdGruppo (long idGruppo);

}


