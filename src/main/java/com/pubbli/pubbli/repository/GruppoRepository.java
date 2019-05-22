package com.pubbli.pubbli.repository;


import com.pubbli.pubbli.model.Dispositivo;
import com.pubbli.pubbli.model.Gruppo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GruppoRepository  extends JpaRepository<Gruppo, Long > {

        Gruppo findByIdGruppo (long idGruppo);


}


