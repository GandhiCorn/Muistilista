/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.activation.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author fuksi
 */
public class Yhteys {

    InitialContext cxt;
    DataSource yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");

    public Yhteys() throws NamingException {
        this.cxt = new InitialContext();
    }
}
