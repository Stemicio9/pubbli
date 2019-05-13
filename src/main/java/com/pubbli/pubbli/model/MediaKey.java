package com.pubbli.pubbli.model;

import java.io.Serializable;

public class MediaKey implements Serializable {


    private long idSequenza;

    private long idMedia;

    public long getIdMedia() {
        return idMedia;
    }

    public long getIdSequenza() {
        return idSequenza;
    }

    public void setIdMedia(long idMedia) {
        this.idMedia = idMedia;
    }

    public void setIdSequenza(long idSequenza) {
        this.idSequenza = idSequenza;
    }
}
