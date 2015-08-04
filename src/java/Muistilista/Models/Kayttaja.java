package Muistilista.Models;

// Käyttäjän konstruktorit ja getterit sekä setteri
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
    private int kayttajaId;

    public Kayttaja() {
    }

    public Kayttaja(String kayttajatunnus, String salasana) {
        this.kayttajatunnus = kayttajatunnus;
        this.salasana = salasana;
    }

    public Kayttaja(String kayttajatunnus, String salasana, int kayttajaId) {
        this.kayttajatunnus = kayttajatunnus;
        this.salasana = salasana;
        this.kayttajaId = kayttajaId;

    }

    public static Kayttaja etsiKayttajaTunnuksilla(String kayttaja, String salasana) throws NamingException, SQLException {
        String sql = "SELECT kayttajanimi, salasana, adminstatus from kayttaja where kayttajanimi = ? AND salasana = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        kysely.setString(2, salasana);
        ResultSet rs = kysely.executeQuery();

        Kayttaja kirjautunut = null;

        if (rs.next()) {
            //Kutsutaan sopivat tiedot vastaanottavaa konstruktoria 
            //ja asetetaan palautettava olio:
            kirjautunut = new Kayttaja();
            kirjautunut.setKayttajatunnus(rs.getString("kayttajanimi"));
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

    public static void luoUusiKayttaja(String kayttajanimi, String salasana) throws NamingException, SQLException {

        String sql = " insert into kayttaja (kayttajanimi, salasana, adminstatus) values (?,?,?)";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttajanimi);
        kysely.setString(2, salasana);
        kysely.setBoolean(3, false);

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

    public static boolean etsiKayttaja(String kayttaja) throws NamingException, SQLException {
        String sql = "SELECT kayttajanimi from kayttaja where kayttajanimi = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        ResultSet rs = kysely.executeQuery();

        //Alustetaan muuttuja, joka sisältää löydetyn käyttäjän
        Kayttaja kirjautunut = null;

        //next-metodia on kutsuttava aina, kun käsitellään 
        //vasta kannasta saatuja ResultSet-olioita.
        //ResultSet on oletuksena ensimmäistä edeltävällä -1:llä rivillä.
        //Kun sitä kutsuu ensimmäisen kerran siirtyy se ensimmäiselle riville 0.
        //Samalla metodi myös palauttaa tiedon siitä onko seuraavaa riviä olemassa.
        if (rs.next()) {
            //Kutsutaan sopivat tiedot vastaanottavaa konstruktoria 
            //ja asetetaan palautettava olio:
            kirjautunut = new Kayttaja();
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

        //Käyttäjä palautetaan vasta täällä, kun resurssit on suljettu onnistuneesti.
        return kirjautunut != null;
    }

    public static List<Kayttaja> getKayttajat() throws SQLException, NamingException {
        String sql = "SELECT KayttajaId, Kayttajatunnus, salasana from kayttajat";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        while (tulokset.next()) {
            //Luodaan tuloksia vastaava olio ja palautetaan olio:
            Kayttaja k = new Kayttaja();
            k.setKayttajaId(tulokset.getInt("kayttajaId"));
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

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public String getSalasana() {
        return salasana;
    }

    public int getKid() {
        return kayttajaId;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public void setKayttajaId(int kayttajaId) {
        this.kayttajaId = kayttajaId;
    }
}
