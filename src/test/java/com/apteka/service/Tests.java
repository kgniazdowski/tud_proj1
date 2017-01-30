package com.apteka.service;

import com.apteka.controller.Controller;
import com.apteka.model.Lek;
import com.apteka.model.Producent;
import org.junit.Before;
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
    private String nazwaLeku = "Februsan";
    private double cenaLeku = 13.99;
    private int iloscLeku = 87;

    @Before
    public void DeleteAll()
    {
        controller.DeleteAllLeki();
        controller.DeleteAllProducents();
    }

    @Test
    public void CheckAddingProducent()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);

        List<Producent> producentList = controller.GetProducenci();
        Producent insertedProducent = producentList.get(0);
        assertEquals(nazwaProducenta, insertedProducent.getNazwa());
        //Usunięcie dodanego producenta
        controller.DeleteAllProducents();
    }

    @Test
    public void CheckGettingProducent()
    {
        List<Producent> producentList = controller.GetProducenci();
        assertEquals(0, producentList.size());
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        producentList = controller.GetProducenci();
        assertEquals(1, producentList.size());
        producent = new Producent(nazwaProducenta + "NEW", miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        producentList = controller.GetProducenci();
        assertEquals(2, producentList.size());
        controller.DeleteAllProducents();
    }

    @Test
    public void CheckDeletingProducent()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        producent = new Producent(nazwaProducenta + "NEW", miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        controller.DeleteProducent("BAYER");
        List<Producent> producentList = controller.GetProducenci();
        assertEquals(1, producentList.size());
        assertEquals(nazwaProducenta + "NEW", producentList.get(0).getNazwa());
        controller.DeleteAllProducents();
    }

    @Test
    public void CheckUpdatingProducent()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        producent = new Producent(nazwaProducenta + "R", miastoProducenta + "R", ulicaProducenta + "R", kodPocztowyProducenta + "R", 9);
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
        controller.DeleteAllProducents();
    }

    @Test
    public void CheckAddLekAndSetProducent()
    {//AddLek jest metodą przeciążoną. Testy dla obu przeciążeń:
        Lek lekBezProducenta = new Lek(nazwaLeku, cenaLeku, iloscLeku);
        controller.AddLek(lekBezProducenta);
        List<Lek> lekList = controller.GetLeki();
        assertEquals(1, lekList.size());
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        Lek lekZProducentem = new Lek("Rutinoborbin", cenaLeku, iloscLeku);
        controller.AddLek(lekZProducentem, nazwaProducenta);
        lekList = controller.GetLeki();
        assertEquals(2, lekList.size());
        //assertEquals(null, lekList.get(0).getProducentId());
        controller.DeleteAllLeki();
        controller.DeleteAllProducents();
    }

    @Test
    public void CheckGettingAllLekByProducent()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        Lek lek = new Lek("Rutinoborbin", cenaLeku, iloscLeku);
        Lek lek2 = new Lek(nazwaLeku, cenaLeku, iloscLeku);
        controller.AddLek(lek, nazwaProducenta);
        controller.AddLek(lek2, nazwaProducenta);

        assertEquals(2, controller.GetLeki().size());

        List<Lek> fromBase = controller.GetLekiByProducentName(nazwaProducenta);
        assertEquals(2, fromBase.size());

        fromBase = controller.GetLekiByProducentName(nazwaProducenta + "ToTylkoTest");
        assertEquals(0, fromBase.size());
    }

    @Test
    public void CheckDeleteProducentFromLek()
    {
        Producent producent = new Producent(nazwaProducenta, miastoProducenta, ulicaProducenta, kodPocztowyProducenta, nrProducenta);
        controller.AddProducent(producent);
        Lek lek = new Lek("Rutinoborbin", cenaLeku, iloscLeku);
        Lek lek2 = new Lek(nazwaLeku, cenaLeku, iloscLeku);
        controller.AddLek(lek, nazwaProducenta);
        controller.AddLek(lek2, nazwaProducenta);

        assertEquals(2, controller.GetLeki().size());

        List<Lek> fromBase = controller.GetLekiByProducentName(nazwaProducenta);
        assertEquals(2, fromBase.size());

        controller.DeleteProducentFromLek(lek);
        fromBase = controller.GetLekiByProducentName(nazwaProducenta);
        assertEquals(1, fromBase.size());
    }
}