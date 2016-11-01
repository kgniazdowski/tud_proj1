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
    public void checkAddingProducent()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);

        List<Producent> producentList = controller.GetProducenci();
        Producent insertedProducent = producentList.get(0);
        assertEquals(nazwaProducenta + "aaa", insertedProducent.getNazwa());
    }

    @Test
    public void checkGettingProducent()
    {
        List<Producent> producentList = controller.GetProducenci();
        assertEquals(1, producentList.size());
        Producent producent = new Producent(nazwaProducenta + "R", miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        assertEquals(2, producentList.size());
    }
}