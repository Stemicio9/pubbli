package com.pubbli.pubbli.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFoto;

    private String urlFoto;

    public long getIdFoto() {
        return idFoto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setIdFoto(long idFoto) {
        this.idFoto = idFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
