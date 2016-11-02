package com.apteka.controller;

import com.apteka.model.Lek;
import com.apteka.model.Producent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karol on 16.10.2016.
 */
public class Controller {

    private Connection connection;
    private String url = "jdbc:hsqldb:hsql://localhost/workdb";
    public String createTableProducent = "CREATE TABLE IF NOT EXISTS Producent (id bigint UNIQUE GENERATED BY DEFAULT AS IDENTITY, nazwa varchar(20) UNIQUE, miasto varchar(20), ulica varchar(20), kodPocztowy varchar(7), nr int);";
    private String createTableLek = "CREATE TABLE IF NOT EXISTS Lek(id bigint GENERATED BY DEFAULT AS IDENTITY, nazwa varchar(20), cena decimal(10,2), ilosc int, producentId int not null FOREIGN KEY REFERENCES Producent (id));";
    //private String createForeignKey = "ALTER TABLE Lek ADD FOREIGN KEY (producentId) REFERENCES Producent (id);";
    private Statement statement;

    private PreparedStatement addProducentStmt;
    private PreparedStatement addLekStmt;
    private PreparedStatement getProducenciStmt;
    private PreparedStatement getProducentByNameStmt;
    private PreparedStatement getLekiByProducentIdStmt;
    private PreparedStatement deleteProducentStmt;
    private PreparedStatement updateProducentStmt;

    public Controller()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            statement.executeUpdate(createTableProducent);
            statement.executeUpdate(createTableLek);
            //statement.executeUpdate(createForeignKey);

            addProducentStmt = connection.prepareStatement("INSERT INTO Producent (nazwa, miasto, ulica, kodPocztowy, nr) VALUES (?, ?, ?, ?, ?)");
            addLekStmt = connection.prepareStatement("INSERT INTO Lek (nazwa, cena, ilosc, producentId) VALUES (?, ?, ?, ?)");
            getProducenciStmt = connection.prepareStatement("SELECT * FROM Producent;");
            getProducentByNameStmt = connection.prepareStatement("SELECT id FROM Producent WHERE nazwa=?;");
            getLekiByProducentIdStmt = connection.prepareStatement("SELECT * from Lek WHERE producentId=?;");
            deleteProducentStmt = connection.prepareStatement("DELETE FROM Producent WHERE nazwa=?;");
            updateProducentStmt = connection.prepareStatement("UPDATE Producent SET nazwa=?, miasto=?, ulica=?, kodPocztowy=?, nr=? WHERE nazwa=?");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void AddProducent(Producent producent)
    {
        try {
            addProducentStmt.setString(1, producent.getNazwa());
            addProducentStmt.setString(2, producent.getMiasto());
            addProducentStmt.setString(3, producent.getUlica());
            addProducentStmt.setString(4, producent.getKodPocztowy());
            addProducentStmt.setInt(5, producent.getNr());
            addProducentStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producent> GetProducenci()
    {
        List<Producent> result = new ArrayList<Producent>();
        try
        {
            ResultSet rs = getProducenciStmt.executeQuery();
            while (rs.next())
            {
                Producent producent = new Producent();
                producent.setNazwa(rs.getString("nazwa"));
                producent.setMiasto(rs.getString("miasto"));
                producent.setUlica(rs.getString("ulica"));
                producent.setKodPocztowy(rs.getString("kodPocztowy"));
                producent.setNr(rs.getInt("nr"));
                result.add(producent);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public boolean DeleteProducent(String producentName)
    {
        try
        {
            deleteProducentStmt.setString(1, producentName);
            deleteProducentStmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateProducent(String producentName, Producent modifiedProducent)
    {
        try
        {
            updateProducentStmt.setString(1, modifiedProducent.getNazwa());
            updateProducentStmt.setString(2, modifiedProducent.getMiasto());
            updateProducentStmt.setString(3, modifiedProducent.getUlica());
            updateProducentStmt.setString(4, modifiedProducent.getKodPocztowy());
            updateProducentStmt.setInt(5, modifiedProducent.getNr());
            updateProducentStmt.setString(6, producentName);
            updateProducentStmt.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean AddLek(Lek lek, String producentName)
    {
        int prodecentId;
        try
        {
            getProducentByNameStmt.setString(1, producentName);
            ResultSet rs = getProducentByNameStmt.executeQuery();

            if (rs.next())
            {
                lek.setProducentId(rs.getInt("id"));
                addLekStmt.setString(1, lek.getNazwa());
                addLekStmt.setDouble(2, lek.getCena());
                addLekStmt.setInt(3, lek.getIlosc());
                addLekStmt.setInt(4, lek.getProducentId());
                addLekStmt.executeUpdate();

                return true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean ProducentExists(String producentName)
    {
        try
        {
            getProducentByNameStmt.setString(1, producentName);
            ResultSet rs = getProducentByNameStmt.executeQuery();

            if (rs.next())
            {
                return true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public int GetProducentIdByName(String producentName)
    {
        try
        {
            getProducentByNameStmt.setString(1, producentName);
            ResultSet rs = getProducentByNameStmt.executeQuery();

            if (rs.next()) {
                 return rs.getInt("id");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Lek> GetLeki(String producentName)
    {
        int producentId = GetProducentIdByName(producentName);
        List<Lek> result = new ArrayList<Lek>();
        try
        {
            getLekiByProducentIdStmt.setInt(1, producentId);
            ResultSet rs = getLekiByProducentIdStmt.executeQuery();
            while (rs.next())
            {
                Lek lek = new Lek();
                lek.setNazwa(rs.getString("nazwa"));
                lek.setCena(rs.getDouble("cena"));
                lek.setIlosc(rs.getInt("ilosc"));
                result.add(lek);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public void PrintMenu()
    {
        System.out.println("MENU");
        System.out.println("a. Dodaj producenta");
        System.out.println("b. Wyswietl producentow");
        System.out.println("c. Dodaj Lek");
        System.out.println("d. Wyswietl leki producenta");
        System.out.println("q. Wyjscie");
    }

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.PrintMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String letter = "";
        letter = br.readLine();
        while (!letter.equals("q"))
        {
            controller.PrintMenu();
            letter = br.readLine();
            if (letter.equals("q"))
            {
                return;
            }
            else if (letter.equals("a")) {
                Producent producent = new Producent();
                System.out.println("Nazwa producenta:");
                producent.setNazwa(br.readLine());
                System.out.println("Miasto producenta:");
                producent.setMiasto(br.readLine());
                System.out.println("Ulica producenta:");
                producent.setUlica(br.readLine());
                System.out.println("Kod pocztowy producenta:");
                producent.setKodPocztowy(br.readLine());
                System.out.println("Numer budynku producenta:");
                producent.setNr(Integer.parseInt(br.readLine()));
                controller.AddProducent(producent);
            }
            else if (letter.equals("b"))
            {
                List<Producent> producentList = controller.GetProducenci();
                if (producentList.isEmpty())
                {
                    System.out.println("Brak producentow w bazie");
                }
                else
                {
                    for (Producent producent : producentList)
                    {
                        System.out.println("Nazwa: " + producent.getNazwa());
                        System.out.println("Miasto: " + producent.getMiasto());
                        System.out.println("Ulica: " + producent.getUlica());
                        System.out.println("Kod pocztowy: " + producent.getKodPocztowy());
                        System.out.println("Numer budynku: " + producent.getNr());
                        System.out.println("===================================================");
                    }
                }
            }
            else if (letter.equals("c"))
            {
                Lek lek = new Lek();
                System.out.println("Nazwa leku:");
                lek.setNazwa(br.readLine());
                System.out.println("Cena leku:");
                lek.setCena(Double.parseDouble(br.readLine()));
                System.out.println("Ilosc leku:");
                lek.setIlosc(Integer.parseInt(br.readLine()));
                System.out.println("Nazwa producenta:");
                if(!controller.AddLek(lek, br.readLine()))
                {
                    System.out.println("Podano zle parametry. Moze zla nazwa producenta?");
                }
            }
            else if (letter.equals("d"))
            {
                System.out.println("Podaj nazwe producenta, ktorego leki chcesz wyswietlic:");
                String producentName = br.readLine();
                if (controller.ProducentExists(producentName))
                {
                    List<Lek> result = controller.GetLeki(producentName);
                    if (!result.isEmpty())
                    {
                        for (Lek lek : result)
                        {
                            System.out.println("Nazwa: " + lek.getNazwa());
                            System.out.println("Cena: " + lek.getCena());
                            System.out.println("Ilosc: " + lek.getIlosc());
                            System.out.println("===================================================");
                        }
                    }
                    else
                        System.out.println("Nie ma jego lekow w bazie");
                }
                else
                    System.out.println("Nie ma takiego producenta w bazie");
            }
            else
            {
                System.out.println("Podano zla litere");
            }
        }
    }
}
