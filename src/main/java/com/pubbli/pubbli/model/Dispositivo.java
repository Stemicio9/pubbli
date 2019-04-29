package com.pubbli.pubbli.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDispositivo;

    private String nomeDispositivo;

    private boolean inAttesa;

    private Date ultimaRichiesta;





    public long getIdDispositivo() {
        return idDispositivo;
    }

    public String getNomeDispositivo() {
        return nomeDispositivo;
    }

    public boolean isInAttesa() {
        return inAttesa;
    }

    public Date getUltimaRichiesta() {
        return ultimaRichiesta;
    }

    public void setIdDispositivo(long idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public void setNomeDispositivo(String nomeDispositivo) {
        this.nomeDispositivo = nomeDispositivo;
    }

    public void setInAttesa(boolean inAttesa) {
        this.inAttesa = inAttesa;
    }

    public void setUltimaRichiesta(Date ultimaRichiesta) {
        this.ultimaRichiesta = ultimaRichiesta;
    }
}


