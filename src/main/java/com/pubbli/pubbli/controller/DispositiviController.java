package com.pubbli.pubbli.controller;


import com.pubbli.pubbli.dto.Nomedispositivoposizione;
import com.pubbli.pubbli.model.Dispositivo;
import com.pubbli.pubbli.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class DispositiviController  {

  @PostMapping("/segnalapresenza")
   public String sonovivo(@RequestBody  Nomedispositivoposizione nmd){

      System.out.println("Sono vivo "+ nmd.getNomedispositivo());
      System.out.println("Sono qui "+ nmd.getLatitudine());
      System.out.println("Sono qua "+ nmd.getLongitudine());

      Dispositivo dispositivoresult = dispositivoRepository.findByNomeDispositivo(nmd.getNomedispositivo());
      if(dispositivoresult == null){

          Dispositivo d =new Dispositivo();
          d.setNomeDispositivo(nmd.getNomedispositivo());
          d.setInAttesa(true);
          d.setUltimaRichiesta(new Date());
          dispositivoRepository.save(d);

      }else{


          dispositivoresult.setUltimaRichiesta(new Date());
          dispositivoRepository.save(dispositivoresult);

      }

      return "CIAO";
  }
    @Autowired
    DispositivoRepository dispositivoRepository ;

    @GetMapping("/alldispositivi")
    public List<Dispositivo> getalldispositivi(){

       return dispositivoRepository.findAll();
    }


}
