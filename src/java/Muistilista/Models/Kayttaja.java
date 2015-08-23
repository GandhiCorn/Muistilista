package Muistilista.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.naming.NamingException;

public class Kayttaja {

    private String kayttajatunnus;
    private String salasana;

    public Kayttaja() {
        this.kayttajatunnus = null;
        this.salasana = null;
    }

    public Kayttaja(String kayttajatunnus, String salasana) {
        this.kayttajatunnus = kayttajatunnus;
        this.salasana = salasana;
    }

    //Palautetaan käyttäjä olio tietokannasta mikäli parametreja vastaava käyttäjä löytyy
    public static Kayttaja etsiKayttajaTunnuksilla(String kayttaja, String salasana) throws NamingException, SQLException {
        String sql = "SELECT kayttajatunnus, salasana from kayttaja where kayttajatunnus = ? AND salasana = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        kysely.setString(2, salasana);
        ResultSet rs = kysely.executeQuery();

        Kayttaja kirjautunut = null;

        if (rs.next()) {
            kirjautunut = new Kayttaja();
            kirjautunut.setKayttajatunnus(rs.getString("kayttajatunnus"));
            kirjautunut.setSalasana(rs.getString("salasana"));
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

        //Käyttäjä palautetaan vasta täällä, kun resurssit on suljettu onnistuneesti.
        return kirjautunut;
    }

    //Listaustestiä varten jokaisen tietokannan käyttäjän listaaminen
    public static List<Kayttaja> getKayttajat() throws NamingException, SQLException {
        String sql = "SELECT kayttajatunnus, salasana from kayttaja";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        while (tulokset.next()) {
            //Luodaan tuloksia vastaava olio ja palautetaan olio:
            Kayttaja k = new Kayttaja();
            k.setKayttajatunnus(tulokset.getString("kayttajatunnus"));
            k.setSalasana(tulokset.getString("salasana"));

            kayttajat.add(k);
        }
        //Suljetaan kaikki resutuloksetsit:
        try {
            tulokset.close();
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

        return kayttajat;
    }

    //palautetaan boolean arvo sille onko tietokannassa parametrin mukana tulevan käyttäjätunnuksen
    //omaavaa käyttäjää.
    public static boolean etsiKayttaja(String kayttaja) throws NamingException, SQLException {
        String sql = "SELECT kayttajatunnus from kayttaja where kayttajatunnus = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        ResultSet rs = kysely.executeQuery();

        Kayttaja uusiKayttaja = null;

        if (rs.next()) {
            uusiKayttaja = new Kayttaja();
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

        return uusiKayttaja != null;
    }

    //Lisätään tietokantaan parametrit omaava käyttäjä
    public static void luoUusiKayttaja(String kayttajaTunnus, String salasana) throws NamingException, SQLException {

        String sql = " insert into kayttaja (kayttajatunnus, salasana) values (?,?)";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttajaTunnus);
        kysely.setString(2, salasana);

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

    public String getKayttajatunnus() {
        return this.kayttajatunnus;
    }

    public String getSalasana() {
        return this.salasana;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }
}
