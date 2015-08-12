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
import javax.naming.NamingException;

/**
 *
 * @author fuksi
 */
public class Luokka {

    private String nimi;
    private int luokkaId;
    private Kayttaja kayttaja;

    public Luokka(String nimi, int luokkaId, Kayttaja kayttaja) {
        this.nimi = nimi;
        this.luokkaId = luokkaId;
        this.kayttaja = kayttaja;
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
}
