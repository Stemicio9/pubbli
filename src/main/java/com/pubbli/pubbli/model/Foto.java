package com.pubbli.pubbli.model;

import javax.persistence.*;

@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="VIDEO_FOTO")

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
