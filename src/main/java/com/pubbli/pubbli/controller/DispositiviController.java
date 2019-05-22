package com.pubbli.pubbli.controller;


import com.pubbli.pubbli.dto.Nomedispositivoposizione;
import com.pubbli.pubbli.dto.Nomeposizionefile;
import com.pubbli.pubbli.model.Dispositivo;
import com.pubbli.pubbli.model.Gruppo;
import com.pubbli.pubbli.model.Media;
import com.pubbli.pubbli.model.Sequenza;
import com.pubbli.pubbli.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.File;
import java.util.*;

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

    @Autowired
    GruppoRepository gruppoRepository;


    @Autowired
    SequenzaRepository sequenzaRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    TuttifileRepository tuttifileRepository;


    @GetMapping("/alldispositivi")
    public List<Dispositivo> getalldispositivi(){

       return dispositivoRepository.findAll();
    }

    @GetMapping("/getsequenza/{idAndroid}")
    public List<Nomeposizionefile> getsequenza(@PathVariable String idAndroid){
        List<Nomeposizionefile> result= new LinkedList<>();



        List<Gruppo> list = gruppideldispisitivo(idAndroid);
        List<Sequenza> lists=prendituttelesequenze(list);
        List<Media> listm=prendituttimediapersequenza(lists);


        for(Media m:listm) {

            String url=tuttifileRepository.findByIdTuttifile(m.getIdMedia()).getUrlTuttifile();
            File f = new File(url);
            Nomeposizionefile nm =new Nomeposizionefile();
            nm.setNomefile(f.getName());
            nm.setPosizione(m.getPosizione());

            result.add(nm);


        }

        return result;

    }

    private List<Gruppo> gruppideldispisitivo(String idAndroid){

        List<Gruppo> list = gruppoRepository.findAll();
        List<Gruppo> result=new LinkedList<>();

        for(Gruppo g : list){

           for(Dispositivo d : g.getDispositivi()){

               if(d.getNomeDispositivo().equals(idAndroid)){
                   result.add(g);
               }
           }
        }
        return result;
    }

    private List<Sequenza> prendituttelesequenze(List<Gruppo> list) {

        List<Sequenza> result = new LinkedList<>();
        for (Gruppo g : list) {

            result.add(sequenzaRepository.findByIdSequenza(g.getIdGruppoSequenza().getIdSequenza()));
        }
        return result;

    }

    private List<Media> prendituttimediapersequenza(List<Sequenza> list){

        List<Media> result= new LinkedList<>();
        for(Sequenza s:list){

            result.addAll( mediaRepository.findAllByIdSequenza(s.getIdSequenza()));
        }

        return result;
    }
}
