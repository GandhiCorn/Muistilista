
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

    //Haetaan tietokannasta kaikki parametrina saatavan käyttäjätunnuksen omaavan käyttäjän luokat
    //ja palautetaan ne luokat sisältävässä listassa
    public static List<Luokka> haeKaikki(String etsittavaKayttaja) throws NamingException, SQLException {
        String sql = "SELECT kayttaja, nimi, luokkaId from luokka where kayttaja = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, etsittavaKayttaja);
        ResultSet rs = kysely.executeQuery();

        List<Luokka> luokat = new ArrayList<Luokka>();

        Luokka loydettyLuokka = null;

        while (rs.next()) {
            loydettyLuokka = new Luokka();
            loydettyLuokka.setKayttaja(rs.getString("kayttaja"));
            loydettyLuokka.setNimi(rs.getString("nimi"));
            loydettyLuokka.setLuokkaId((Integer) rs.getObject("luokkaId"));
            luokat.add(loydettyLuokka);
        }

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

    //Poistetaan luokka tietokannasta, jolla on parametrin luokkaId.
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

    //Lisätään servlet puolella alustettu luokka olio tietokantaan 
    public void lisaaKantaan() throws NamingException, SQLException {
        String sql = "insert into luokka (nimi, kayttaja) values (?,?) RETURNING luokkaid";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, this.getNimi());
        kysely.setString(2, this.getKayttaja());

        //PostgreSQL:n ominaisuus, jolla pystyy lisäämään SERIAL tyyppiselle pääaavaimelle id:n automaattisesti
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

    //Palautetaan boolean arvo sille löytyykö parametrin nimistä luokkaa tietokannasta
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
    
    //Nimi setteri, jossa tarkistetaan ettei nimi ole tyhjä
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

    //palautetaan true mikäli virheet lista on tyhjä 
    public boolean onkoKelvollinen() {
        return this.virheet.isEmpty();
    }

    //palautetaan virhe listan virheet
    public Collection<String> getVirheet() {
        return virheet.values();
    }
}
