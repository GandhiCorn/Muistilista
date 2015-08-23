package Muistilista.Models;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Yhteys extends HttpServlet {

    //Luodaan yhteys tietokantaan 
    public static Connection getYhteys() throws NamingException, SQLException {
        InitialContext cxt = new InitialContext();
        DataSource dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
        return dataSource.getConnection();
    }

}
