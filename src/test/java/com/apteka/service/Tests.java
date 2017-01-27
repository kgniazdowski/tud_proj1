package com.apteka.service;

import com.apteka.controller.Controller;
import com.apteka.model.Producent;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by karol on 31.10.2016.
 */
public class Tests {

    Controller controller = new Controller();
    private String nazwaProducenta = "BAYER";
    private String miastoProducenta = "Gdynia";
    private String ulicaProducenta = "Borewicza";
    private String kodPocztowyProducenta = "80-428";
    private int nrProducenta = 12;

    @Test
    public void CheckAddingProducent()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);

        List<Producent> producentList = controller.GetProducenci();
        Producent insertedProducent = producentList.get(0);
        assertEquals(nazwaProducenta, insertedProducent.getNazwa());
    }

    /*@Test
    public void CheckGettingProducent()
    {
        List<Producent> producentList = controller.GetProducenci();
        assertEquals(1, producentList.size());
        Producent producent = new Producent(nazwaProducenta + "R", miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        producentList = controller.GetProducenci();
        assertEquals(2, producentList.size());
    }

    @Test
    public void CheckDeletingProducent()
    {
        controller.DeleteProducent("BAYERR");
        List<Producent> producentList = controller.GetProducenci();
        assertEquals(1, producentList.size());
        assertEquals(nazwaProducenta, producentList.get(0).getNazwa());
    }

    @Test
    public void CheckUpdatingProducent()
    {
        Producent producent = new Producent(nazwaProducenta + "R", miastoProducenta + "R", ulicaProducenta + "R", kodPocztowyProducenta + "R", 9);
        controller.UpdateProducent(nazwaProducenta, producent);
        List<Producent> producentList = controller.GetProducenci();
        assertEquals(1, producentList.size());
        assertEquals("BAYERR", producentList.get(0).getNazwa());
        assertEquals(miastoProducenta + "R", producentList.get(0).getMiasto());
        assertEquals(ulicaProducenta + "R", producentList.get(0).getUlica());
        assertEquals(kodPocztowyProducenta + "R", producentList.get(0).getKodPocztowy());
        assertEquals(9, producentList.get(0).getNr());
        producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.UpdateProducent("BAYERR", producent);
        producentList = controller.GetProducenci();
        assertEquals("BAYER", producentList.get(0).getNazwa());
    }*/
}