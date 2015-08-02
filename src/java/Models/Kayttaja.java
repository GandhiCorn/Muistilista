package Models;

// Käyttäjän konstruktorit ja getterit sekä setteri
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static Models.Yhteys.luoTietokantaYhteys;
import static Models.Yhteys.suljeYhteys;
import java.sql.SQLException;

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

    public static List<Kayttaja> getKayttajat() throws SQLException {
        String sql = "SELECT KayttajaId, Kayttajatunnus, salasana from kayttajat";
        Connection yhteys = luoTietokantaYhteys();
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
