package com.pubbli.pubbli.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tuttifile {

    @Id

    private long idTuttifile;

    private String urlTuttifile;

    public long getIdTuttifile() {
        return idTuttifile;
    }

    public String getUrlTuttifile() {
        return urlTuttifile;
    }

    public void setIdTuttifile(long idTuttifile) {
        this.idTuttifile = idTuttifile;
    }

    public void setUrlTuttifile(String urltuttifile) {
        this.urlTuttifile = urltuttifile;
    }
}
