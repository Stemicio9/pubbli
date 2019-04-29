package com.pubbli.pubbli.repository;

import com.pubbli.pubbli.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long > {


   Dispositivo findByNomeDispositivo(String nomeDispositivo);

}
