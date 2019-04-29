package com.pubbli.pubbli.controller;

import com.pubbli.pubbli.dto.Grupposequenza;
import com.pubbli.pubbli.dto.Nomedispositivoposizione;
import com.pubbli.pubbli.model.Dispositivo;
import com.pubbli.pubbli.model.Gruppo;
import com.pubbli.pubbli.model.Sequenza;
import com.pubbli.pubbli.repository.DispositivoRepository;
import com.pubbli.pubbli.repository.GruppoRepository;
import com.pubbli.pubbli.repository.SequenzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class GruppoController {

    @GetMapping("/allgruppi")
    public List<Gruppo> allgruppi(){

        return gruppoRepository.findAll();
    }

    @PostMapping("/creagruppi")
    public void creagruppi(@RequestBody Gruppo gruppo){


            System.out.println("CHIAMATA A CREA GRUPPO");
            gruppoRepository.save(gruppo);


    }

    @PostMapping("/rimuovigruppi")
    public void rimuovigruppi(@RequestBody Gruppo gruppo){

        gruppoRepository.delete(gruppo);
    }

    @Autowired
    GruppoRepository gruppoRepository ;

    @Autowired
    SequenzaRepository sequenzaRepository;

    @Autowired
    DispositivoRepository dispositivoRepository;


    @PostMapping("/assegnasequenzaagruppo")
    public void assegnasequenzaagruppo(@RequestBody Grupposequenza grupposequenza){

        Gruppo gruppocorrente= gruppoRepository.findByIdGruppo(grupposequenza.getIdgruppo());

        Sequenza sequenzacorrente = sequenzaRepository.findById(grupposequenza.getIdsequenza()).get();

        gruppocorrente.setIdGruppoSequenza(sequenzacorrente);


        gruppoRepository.save(gruppocorrente);
    }

    @PostMapping("assegnadispositivoagruppo")
    public void assegnadispositivoagruppo(@RequestBody Grupposequenza grupposequenza){

        Gruppo gruppocorrente= gruppoRepository.findByIdGruppo(grupposequenza.getIdgruppo());

        Dispositivo dispositivocorrente= dispositivoRepository.findById(grupposequenza.getIdsequenza()).get();

        gruppocorrente.getDispositivi().add(dispositivocorrente);

        gruppoRepository.save(gruppocorrente);

    }


}
