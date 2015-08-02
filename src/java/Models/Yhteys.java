package Models;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;

// Luo tietokanta yhteyden ja muutamat metodit
public class Yhteys {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/tuharo";
    static final String tietokantaKayttaja = "tuharo";
    static final String tietokantaSalasana = "f6b18a96f675fec7";

    public Yhteys() {
    }

    // Luo tietokantayhteyden
    public Connection luoTietokantaYhteys() {

        try {
            Class.forName(JDBC_DRIVER).newInstance();
            return DriverManager.getConnection(DB_URL, tietokantaKayttaja, tietokantaSalasana);
        } catch (Exception e) {

            return null;
        }
    }

    // palauttaa käyttäjän nimen
    public String getKayttajanNimi(String tunnus) {
        Connection yhteys = luoTietokantaYhteys();
        PreparedStatement kysely;
        try {
            kysely = yhteys.prepareStatement("SELECT KAYTTAJATUNNUS FROM KAYTTAJA WHERE SALASANA=?");
            kysely.setString(1, tunnus);
            ResultSet resultset = kysely.executeQuery();
            if (resultset.next()) {
                String nimi = resultset.getString("KAYTTAJATUNNUS");
                suljeYhteys(resultset, kysely, yhteys);
                return nimi;

            }
            suljeYhteys(resultset, kysely, yhteys);
        } catch (SQLException ex) {

        }
        return null;
    }

    // tarkastaa onko salasanan ja tunnus oikein
    public boolean salasanaJaTunnusOikein(String tunnus, String salasana) {
        Connection yhteys = luoTietokantaYhteys();
        PreparedStatement kysely;
        try {
            kysely = yhteys.prepareStatement("SELECT TUNNUS, SALASANA FROM KAYTTAJA WHERE TUNNUS=? AND SALASANA=?");
            kysely.setString(1, tunnus);
            kysely.setString(2, salasana);
            ResultSet resultset = kysely.executeQuery();
            if (resultset.next()) {
                suljeYhteys(resultset, kysely, yhteys);
                return true;
            }
            suljeYhteys(resultset, kysely, yhteys);
        } catch (SQLException ex) {

        }
        return false;

    }

// sulkee yhteyden
    private void suljeYhteys(ResultSet resultset, PreparedStatement kysely, Connection conn) throws SQLException {
        resultset.close();
        kysely.close();
        conn.close();
    }


}
