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
public class Askare {

    


    private Map<String, String> virheet = new HashMap<String, String>();
    private String nimi;
    private int tarkeys;
    private String kayttaja;
    private int askareenId;
    private String luokka;

    public Askare() {
    }

    public Askare(int askareenId, String nimi, int tarkeys, String luokka, String kayttaja) {
        this.askareenId = askareenId;
        this.luokka = luokka;
        this.nimi = nimi;
        this.tarkeys = tarkeys;
        this.kayttaja = kayttaja;

    }
    
        /*  Luokkaa poistaessa poistetaan myös sen luokan kaikki askareet defaultina,
        joten tarvitaan tälläinen metodi poistamaan poistettavan luokan askareiden luokat*/
    public static void poistaAskareenLuokka(String nimi) throws NamingException, SQLException {

        String sql = "update askare set luokka = ? where luokka = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, null);
        kysely.setString(2, nimi);

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
    
    //Metodilla päivitetään askareen tiedot tietokanssa 
    public void paivitaAskare() throws NamingException, SQLException {
        String sql = "update askare set tarkeysArvo = ?, nimi = ?, kayttaja = ?, luokka = ? WHERE askareenId = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getTarkeys());
        kysely.setString(2, this.getNimi());
        kysely.setString(3, this.getKayttaja());
        kysely.setString(4, this.getLuokka());
        kysely.setInt(5, this.getAskareenId());

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

    //Poistetaan parametrin id:n omaava askare tietokannasta
    public static void poistaAskare(int id) throws NamingException, SQLException {

        String sql = "delete from askare where askareenId = ?";
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

    //Lisätään tietokantaan askare olio jolle ollaan alustettu servletin puolella tarvittavat tiedot
    public void lisaaKantaan() throws NamingException, SQLException {
        String sql = "insert into askare (tarkeysarvo, nimi, kayttaja, luokka) values (?,?,?,?) RETURNING askareenid";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getTarkeys());
        kysely.setString(2, this.getNimi());
        kysely.setString(3, this.getKayttaja());
        kysely.setString(4, this.getLuokka());
        
        //PostgreSQL osaa jakaa SERIAL tyyppiselle pääavaimelle automaattisesti vapaan id:n
        ResultSet ids = kysely.executeQuery();
        ids.next();

        this.askareenId = ids.getInt(1);

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

    
    //Etsitään askare, jolla on parametrin id ja palautetaan sen olio
    public static Askare etsiAskare(int id) throws NamingException, SQLException {

        String sql = "select askareenid, tarkeysarvo, nimi, kayttaja, luokka from askare where askareenid = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();

        //Alustetaan muuttuja, joka sisältää löydetyn askareen
        Askare loydettyAskare = null;

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
            loydettyAskare.setNimi1(rs.getString("nimi"));
            loydettyAskare.setKayttaja(rs.getString("kayttaja"));
            loydettyAskare.setLuokka1(rs.getString("luokka"));

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

        return loydettyAskare;

    }

    //Haetaan kaikki käyttäjän askareet tietokannasta, lisätään ne Askare Listille ja palautetaan se.
    //Parametrina etsittävän käyttäjän käyttäjätunnus
    public static List<Askare> etsiAskareet(String kayttaja) throws NamingException, SQLException {

        String sql = "select askareenid, tarkeysarvo, nimi, kayttaja, luokka from askare where kayttaja = ?";
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
            loydettyAskare.setNimi2(rs.getString("nimi"));
            loydettyAskare.setKayttaja(rs.getString("kayttaja"));
            loydettyAskare.setLuokka1(rs.getString("luokka"));

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

    public String getLuokka() {
        return this.luokka;
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

    public void setAskareenId(int uusiId) {
        this.askareenId = uusiId;
    }

    //Simppeli nimen asetus
    public void setNimi1(String uusiNimi) {
        this.nimi = uusiNimi;
    }

    //Tarkistetaan nimen oikeellisuus ja lisätään virheet listaan jos niitä löytyy
    public void setNimi2(String uusiNimi) {
        if (uusiNimi.trim().length() == 0) {
            virheet.put("nimi", "Nimi ei saa olla tyhjä");
        } else {
            this.nimi = uusiNimi;
            virheet.remove("nimi");
        }
    }

    public void setTarkeys(int uusiTarkeys) {
        if (uusiTarkeys <= 0) {
            virheet.put("tarkeys", "Askareella täytyy olla positiivinen tärkeysarvo");
        } else {
            this.tarkeys = uusiTarkeys;
            virheet.remove("tarkeys");
        }
    }

    //parsetaan string tyyppinen tärkeys intiin ja kutsutaan toista tärkeyden setteritä
    public void setTarkeys(String uusiTarkeys) {
        if (!uusiTarkeys.equals("")) {
            try {
                setTarkeys(Integer.parseInt(uusiTarkeys));
                virheet.remove("tarkeys");
            } catch (NumberFormatException e) {
                virheet.put("tarkeys", "Askareen tärkeysarvon tulee olla kokonaisluku");
            }
        }
    }

    //Simppeli luokan setteri
    public void setLuokka1(String uusiLuokka) {
        this.luokka = uusiLuokka;
    }

    //Luokan setteri muutamalla tarkistuksella
    public void setLuokka2(String luokka) throws NamingException, SQLException {
        if (!luokka.equals("")) {
            if (!Luokka.etsi(luokka)) {
                this.virheet.put("luokka_id", "Luokkaa ei löytynyt tietokannasta");
            } else {
                this.luokka = luokka;
                virheet.remove("luokka_id");
            }
        }
    }

    //palautetaan true, mikäli virheet lista on tyhjä
    public boolean onkoKelvollinen() {
        return this.virheet.isEmpty();
    }

    //palautetaan virhe listan kaikki virheet
    public Collection<String> getVirheet() {
        return virheet.values();
    }
}
