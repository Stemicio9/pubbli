package com.pubbli.pubbli.controller;


import com.pubbli.pubbli.model.Sequenza;
import com.pubbli.pubbli.repository.SequenzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SequenzaController {

    @Autowired
    SequenzaRepository sequenzaRepository;

    @GetMapping("/getallsequenze")
    public List<Sequenza> allsquenze(){

        return sequenzaRepository.findAll();
    }

    @PostMapping("/creasequenze")
    public void creasequenze(@RequestBody Sequenza sequenza){

        sequenzaRepository.save(sequenza);
    }

    @PostMapping("/rimuovisequenze")
      public void rimuovisequenze(@RequestBody Sequenza sequenza) {
        sequenzaRepository.delete(sequenza);
    }

}
