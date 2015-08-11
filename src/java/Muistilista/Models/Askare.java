/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Muistilista.Models;

import Muistilista.Servlets.AskareS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author fuksi
 */
public class Askare {


    private String nimi;
    private int tarkeys;
    private String kayttaja;
    private int askareenId;
    private int luokkaId;

    public Askare() {
        this.askareenId = -1;
        this.luokkaId = -1;
        this.nimi = null;
        this.tarkeys = -1;
        this.kayttaja = null;
    }

    public Askare(int askareenId, String nimi, int tarkeys, int luokkaId, String kayttaja) {
        this.askareenId = askareenId;
        this.luokkaId = luokkaId;
        this.nimi = nimi;
        this.tarkeys = tarkeys;
        this.kayttaja = kayttaja;
    }
    
        public static List<Askare> etsiAskare(int id) throws NamingException, SQLException {

        String sql = "select askareenid, tarkeysarvo, nimi, kayttaja, luokkaid from askare where askareenid = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();

        //Alustetaan muuttuja, joka sisältää löydetyn askareen
        Askare loydettyAskare = null;

        List<Askare> askareet = new ArrayList<Askare>();

        //next-metodia on kutsuttava aina, kun käsitellään 
        //vasta kannasta saatuja ResultSet-olioita.
        //ResultSet on oletuksena ensimmäistä edeltävällä -1:llä rivillä.
        //Kun sitä kutsuu ensimmäisen kerran siirtyy se ensimmäiselle riville 0.
        //Samalla metodi myös palauttaa tiedon siitä onko seuraavaa riviä olemassa.
        while (rs.next()) {
            //Kutsutaan sopivat tiedot vastaanottavaa konstruktoria 
            //ja asetetaan palautettava olio:
            loydettyAskare = new Askare();
            loydettyAskare.setAskareenId((Integer) rs.getObject("askareenid"));
            loydettyAskare.setTarkeys((Integer) rs.getObject("tarkeysarvo"));
            loydettyAskare.setNimi(rs.getString("nimi"));
            loydettyAskare.setKayttaja(rs.getString("kayttaja"));         
            loydettyAskare.setLuokkaId((Integer) rs.getObject("luokkaid"));

            askareet.add(loydettyAskare);

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

        return askareet;

    }

    public static List<Askare> etsiAskareet(String kayttaja) throws NamingException, SQLException {

        String sql = "select askareenid, tarkeysarvo, nimi, kayttaja, luokkaid from askare where kayttaja = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        ResultSet rs = kysely.executeQuery();

        //Alustetaan muuttuja, joka sisältää löydetyn askareen
        Askare loydettyAskare = null;

        List<Askare> askareet = new ArrayList<Askare>();

        //next-metodia on kutsuttava aina, kun käsitellään 
        //vasta kannasta saatuja ResultSet-olioita.
        //ResultSet on oletuksena ensimmäistä edeltävällä -1:llä rivillä.
        //Kun sitä kutsuu ensimmäisen kerran siirtyy se ensimmäiselle riville 0.
        //Samalla metodi myös palauttaa tiedon siitä onko seuraavaa riviä olemassa.
        while (rs.next()) {
            //Kutsutaan sopivat tiedot vastaanottavaa konstruktoria 
            //ja asetetaan palautettava olio:
            loydettyAskare = new Askare();
            loydettyAskare.setAskareenId((Integer) rs.getObject("askareenid"));
            loydettyAskare.setTarkeys((Integer) rs.getObject("tarkeysarvo"));
            loydettyAskare.setNimi(rs.getString("nimi"));
            loydettyAskare.setKayttaja(rs.getString("kayttaja"));         
            loydettyAskare.setLuokkaId((Integer) rs.getObject("luokkaid"));

            askareet.add(loydettyAskare);

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

        return askareet;

    }

    public int getAskareenId() {
        return this.askareenId;
    }

    public int getLuokkaId() {
        return this.luokkaId;
    }

    public int getTarkeys() {
        return this.tarkeys;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getKayttaja() {
        return this.kayttaja;
    }

    public void setKayttaja(String kayttaja) {
        this.kayttaja = kayttaja;
    }

    public void setAskareenId(int aId) {
        this.askareenId = aId;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setTarkeys(int tarkeys) {
        this.tarkeys = tarkeys;
    }

    public void setLuokkaId(int luokkaId) {
        this.luokkaId = luokkaId;
    }
}