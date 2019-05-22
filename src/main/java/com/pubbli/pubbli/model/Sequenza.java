package com.pubbli.pubbli.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Sequenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSequenza;

    private String nomeSequenza;

    //CREARE QUEERY CHE ASSEGNA LA SEQUENZA




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
