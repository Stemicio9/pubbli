package com.pubbli.pubbli.controller;


import com.pubbli.pubbli.model.Foto;
import com.pubbli.pubbli.model.Media;
import com.pubbli.pubbli.model.Sequenza;
import com.pubbli.pubbli.model.Video;
import com.pubbli.pubbli.repository.FotoRepository;
import com.pubbli.pubbli.repository.MediaRepository;
import com.pubbli.pubbli.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin()
@RestController
public class MediaController {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    FotoRepository fotoRepository;

    @Autowired
    VideoRepository videoRepository;


    @PostMapping("/updatesequenza")
    @Transactional
    public boolean updateSequenza(@RequestBody List<Media> list){

        if(list.isEmpty() || list.size()==0) return false;

        mediaRepository.deleteAllByIdSequenza(list.get(0).getIdSequenza());


        for(Media m: list){

            mediaRepository.save(m);
        }

         return true;

    }

    @PostMapping ("/getallsequenza")
    public List<Media> getAllSequenza(@RequestBody Sequenza sequenza){


        return mediaRepository.findAllByIdSequenza(sequenza.getIdSequenza());
    }

    @PostMapping("/isfotovideo")
    public boolean isFotoVideo(@RequestBody long idFotoVideo){

        //System.out.println("ID = "+idFotoVideo);
        List<Foto> lf=fotoRepository.findAll();
        List<Video> lv=videoRepository.findAll();



        for (Foto f : lf) {

             if (idFotoVideo == f.getIdFoto()) return true;
            System.out.println("ID = "+idFotoVideo);
        }

        for (Video v : lv) {

             if (idFotoVideo == v.getIdVideo()) return false;
        }

        return false;

     }






    @PostMapping("/isfoto")
    public boolean isFoto(@RequestBody long idFoto){

        List<Foto> lf=fotoRepository.findAll();


        for (Foto f: lf){

            if(idFoto == f.getIdFoto()) return true;

            else return false;
        }

        return true;
    }


}
