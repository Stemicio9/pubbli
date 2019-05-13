package com.pubbli.pubbli.controller;


import com.pubbli.pubbli.model.Sequenza;

import com.pubbli.pubbli.model.Video;
import com.pubbli.pubbli.repository.SequenzaRepository;
import com.pubbli.pubbli.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
public class SequenzaController {

    @Autowired
    SequenzaRepository sequenzaRepository;

    @Autowired
    VideoRepository videoRepository;

    @GetMapping("/getallsequenze")
    public List<Sequenza> allsequenze(){

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
