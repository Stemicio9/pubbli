package com.pubbli.pubbli.dto;

public class Nomedispositivoposizione {


    private String nomedispositivo;

    private double latitudine;

    private double longitudine;

    private long idAndroid;

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
