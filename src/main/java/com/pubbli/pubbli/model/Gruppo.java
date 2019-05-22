package com.pubbli.pubbli.model;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Gruppo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGruppo;

    private String nomeGruppo;



    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "grupposequenza",
            joinColumns =  @JoinColumn(name ="idGruppo"),inverseJoinColumns= @JoinColumn(name="idSequenza"))
    public Sequenza idGruppoSequenza;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gruppodisposivo",
            joinColumns =  @JoinColumn(name ="idGruppo" ),inverseJoinColumns= @JoinColumn(name="idDispositivo " ))

    public Set<Dispositivo> gdispositivi;



    public long getIdGruppo() {
        return idGruppo;
    }

    public String getNomeGruppo() {
        return nomeGruppo;
    }

    public void setIdGruppo(long idGruppo) {
        this.idGruppo = idGruppo;
    }

    public void setNomeGruppo(String nomeGruppo) {
        this.nomeGruppo = nomeGruppo;
    }

    public Sequenza getIdGruppoSequenza() {
        return idGruppoSequenza;
    }

    public void setIdGruppoSequenza(Sequenza idGruppoSequenza) {
        this.idGruppoSequenza = idGruppoSequenza;
    }

    public Set<Dispositivo> getDispositivi() {
        return gdispositivi;
    }

    public void setDispositivi(Set<Dispositivo> gdispositivi) {
        this.gdispositivi = gdispositivi;
    }
}
