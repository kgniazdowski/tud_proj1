package com.apteka.model;

/**
 * Created by karol on 16.10.2016.
 */
public class Lek {
    public String nazwa;
    public double cena;
    public int ilosc;
    public int producentId;

    public String getNazwa() {
        return nazwa;
    }

    public double getCena() {
        return cena;
    }

    public int getIlosc() {
        return ilosc;
    }

    public int getProducentId() {
        return producentId;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public void setProducentId(int producentId) {
        this.producentId = producentId;
    }
}
