package com.pubbli.pubbli.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sequenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSequenza;

    private String nomeSequenza;

    public long getIdSequenza() {
        return idSequenza;
    }

    public String getNomeSequenza() {
        return nomeSequenza;
    }


    public void setIdSequenza(long idSequenza) {
        this.idSequenza = idSequenza;
    }

    public void setNomeSequenza(String nomeSequenza) {
        this.nomeSequenza = nomeSequenza;
    }
}
