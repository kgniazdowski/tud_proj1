package com.apteka.model;

/**
 * Created by karol on 16.10.2016.
 */
public class Producent {
    public String nazwa;
    public String miasto;
    public String ulica;
    public String kodPocztowy;
    public int nr;

    public Producent()
    {

    }

    public Producent(String nazwa, String miasto, String ulica, String kodPocztowy, int nr) {
        this.nazwa = nazwa;
        this.miasto = miasto;
        this.ulica = ulica;
        this.kodPocztowy = kodPocztowy;
        this.nr = nr;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public int getNr() {
        return nr;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }
}
