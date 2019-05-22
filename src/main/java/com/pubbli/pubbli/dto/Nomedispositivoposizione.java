package com.pubbli.pubbli.dto;

import com.pubbli.pubbli.model.Gruppo;

import java.util.List;

public class Nomedispositivoposizione {


    private String nomedispositivo;

    private double latitudine;

    private double longitudine;

    private long idAndroid;

   // private List<Gruppo> list;

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public String getNomedispositivo() {
        return nomedispositivo;
    }

    public long getIdAndroid() {
        return idAndroid;
    }

  /*  public List<Gruppo> getList() {
        return list;
    }

    public void setList(List<Gruppo> list) {
        this.list = list;
    }*/

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public void setNomedispositivo(String nomedispositivo) {
        this.nomedispositivo = nomedispositivo;
    }

    public void setIdAndroid(long idAndroid) {
        this.idAndroid = idAndroid;
    }
}
