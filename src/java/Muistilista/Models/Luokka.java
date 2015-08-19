/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Muistilista.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author fuksi
 */
public class Luokka {

    public static List<Integer> tyhjennaLuokanAskareet(String kayttaja, int id) throws NamingException, SQLException {
        String sql = "Select askareenId from askare where kayttajaTunnus = ? and luokkaId = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        kysely.setInt(2, id);
        ResultSet rs = kysely.executeQuery();

        List<Integer> askareet = new ArrayList<Integer>();

        while (rs.next()) {
            askareet.add((Integer) rs.getObject("askareenId"));
        }

        try {
            rs.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
        
        return askareet;
    }

    private Map<String, String> virheet = new HashMap<String, String>();
    private String nimi;
    private int luokkaId;
    private String kayttaja;

    public Luokka() {

    }

    public Luokka(String nimi, int luokkaId, String kayttaja) {
        this.nimi = nimi;
        this.luokkaId = luokkaId;
        this.kayttaja = kayttaja;
    }

    public static List<Luokka> haeKaikki(String etsittavaKayttaja) throws NamingException, SQLException {
        String sql = "SELECT kayttaja, nimi, luokkaId from luokka where kayttaja = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, etsittavaKayttaja);
        ResultSet rs = kysely.executeQuery();

        List<Luokka> luokat = new ArrayList<Luokka>();

        Luokka loydettyLuokka = null;

        while (rs.next()) {
            //Kutsutaan sopivat tiedot vastaanottavaa konstruktoria 
            //ja asetetaan palautettava olio:
            loydettyLuokka = new Luokka();
            loydettyLuokka.setKayttaja(rs.getString("kayttaja"));
            loydettyLuokka.setNimi(rs.getString("nimi"));
            loydettyLuokka.setLuokkaId((Integer) rs.getObject("luokkaId"));
            luokat.add(loydettyLuokka);
        }

        //Jos kysely ei tuottanut tuloksia käyttäjä on nyt vielä null.
        //Suljetaan kaikki resurssit:
        try {
            rs.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
        return luokat;
    }

    public static void poistaLuokka(int id) throws NamingException, SQLException {

        String sql = "delete from luokka where luokkaId = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);

        kysely.executeUpdate();
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
    }

    public void lisaaKantaan() throws NamingException, SQLException {
        String sql = "insert into luokka (nimi, kayttaja) values (?,?) RETURNING luokkaid";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, this.getNimi());
        kysely.setString(2, this.getKayttaja());

        ResultSet ids = kysely.executeQuery();
        ids.next();

        this.luokkaId = ids.getInt(1);

        try {
            ids.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }

    }

    public static boolean etsi(String luokanNimi) throws NamingException, SQLException {
        String sql = "SELECT nimi from luokka where nimi = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, luokanNimi);
        ResultSet rs = kysely.executeQuery();

        int loytyi = 0;

        if (rs.next()) {
            loytyi = 1;
        }

        //Jos kysely ei tuottanut tuloksia käyttäjä on nyt vielä null.
        //Suljetaan kaikki resurssit:
        try {
            rs.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
        if (loytyi == 1) {
            return true;
        }
        return false;
    }

    public String getKayttaja() {
        return this.kayttaja;
    }

    public void setKayttaja(String uusiKayttaja) {
        this.kayttaja = uusiKayttaja;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setNimi(String uusiNimi) {
        if (uusiNimi.trim().length() == 0) {
            virheet.put("nimi", "Nimi ei saa olla tyhjä");
        } else {
            this.nimi = uusiNimi;
            virheet.remove("nimi");
        }
    }

    public int getLuokkaId() {
        return this.luokkaId;
    }

    public void setLuokkaId(int uusiId) {
        this.luokkaId = uusiId;
    }

    public boolean onkoKelvollinen() {
        return this.virheet.isEmpty();
    }

    public Collection<String> getVirheet() {
        return virheet.values();
    }
}
