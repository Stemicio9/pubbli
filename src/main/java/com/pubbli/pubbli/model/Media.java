package com.pubbli.pubbli.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MediaKey.class)
public class Media {

    @Id
    private long idSequenza;

    @Id
    private long idMedia;


    private int posizione;



    public long getIdSequenza() {
        return idSequenza;
    }

    public int getPosizione() {
        return posizione;
    }

    public long getIdMedia() {
        return idMedia;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public void setIdSequenza(long idSequenza) {
        this.idSequenza = idSequenza;
    }

    public void setIdMedia(long idMedia) {
        this.idMedia = idMedia;
    }
}
