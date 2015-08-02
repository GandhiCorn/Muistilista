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
    public static Connection luoTietokantaYhteys() {

        try {
            Class.forName(JDBC_DRIVER).newInstance();
            return DriverManager.getConnection(DB_URL, tietokantaKayttaja, tietokantaSalasana);
        } catch (Exception e) {

            return null;
        }
    }

    // Sulkee yhteyden jos sellainen on käynnissä
    public static void suljeYhteys(ResultSet resultset, PreparedStatement kysely, Connection conn) throws SQLException {
        resultset.close();
        kysely.close();
        conn.close();
    }

}
