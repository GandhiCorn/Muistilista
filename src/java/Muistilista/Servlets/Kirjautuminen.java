package Muistilista.Servlets;

import Muistilista.Models.Kayttaja;
import Muistilista.Models.Luokka;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Kirjautuminen ja rekisteröityminen
public class Kirjautuminen extends ToistuvaKoodi {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");

            String salasana = request.getParameter("password");
            String kayttaja = request.getParameter("username");
            String nappi = request.getParameter("subject");

            //jos nappia ei paineta jatketaan tänne tai jos napin arvo oli login
            if (nappi == null || nappi.equals("login")) {
                //pelkkä lomake
                if (kayttaja == null || salasana == null) {
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //ei käyttäjätunnusta
                if (kayttaja == null || kayttaja.equals("")) {
                    asetaVirhe("Kirjautuminen epäonnistui! Et antanut käyttäjätunnusta.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                if (kayttaja.length() > 20) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Käyttäjätunnuksen maksimipituus on 20 merkkiä", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //kerrotaan käyttäjätunnus ettei jää tyhjäksi kerran kun on jotain laitettu
                request.setAttribute("kayttaja", kayttaja);
                //ei salasanaa
                if (salasana == null || salasana.equals("")) {
                    asetaVirhe("Kirjautuminen epäonnistui! Et antanut salasanaa.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //tarkistetaan onko käyttäjä olemassa ja välitetään eteenpäin tai annetaan virheilmoitus
                HttpSession session = request.getSession();
                Kayttaja client = Kayttaja.etsiKayttajaTunnuksilla(kayttaja, salasana);
                if (client == null) {
                    asetaVirhe("Kirjautuminen epäonnistui! Käyttäjää ei löytynyt järjestelmästä", request);
                    naytaJSP("login.jsp", request, response);
                } else {
                    session.setAttribute("kirjautunut", client);
                    session.setAttribute("luokat", Luokka.haeKaikki(kayttaja));
                    response.sendRedirect("Index");
                }
            } //jos taas napin arvo oli newuser eli luo uusi käyttäjä nappia on painettu
            else if (nappi.equals("newuser")) {

                //näytetään ensin pelkkä lomake
                if (kayttaja == null || salasana == null) {
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //jos käyttäjää ei olltu syötetty
                if (kayttaja.equals("")) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Et antanut käyttäjätunnusta.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //jos käyttäjä tunnuksen nimen pituus on yli maksimiarvon
                if (kayttaja.length() > 20) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Käyttäjätunnuksen maksimipituus on 20 merkkiä", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //asetetaan hyväksytty käyttäjänimi, jottei sitä tarvitse syöttää uudestaan
                request.setAttribute("kayttaja", kayttaja);

                //jos salasana on tyhjä
                if (salasana.equals("")) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Et antanut salasanaa.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //jos salasana on liian pitkä
                if (salasana.length() > 20) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Salasanan maksimipituus on 20 merkkiä.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //tarkistetaan ettei käyttäjää ole jo olemassa
                HttpSession session = request.getSession();
                boolean loytyykoKayttaja = Kayttaja.etsiKayttaja(kayttaja);
                if (loytyykoKayttaja) {
                    asetaVirhe("Käyttäjätunnus on varattu. Kokeile toista.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                } else {
                    //luodaan uusi käyttäjä ja lisätään se tietokantaan
                    Kayttaja.luoUusiKayttaja(kayttaja, salasana);
                    session.setAttribute("ilmoitus", "Käyttäjä luotu onnistuneesti. Voit nyt kirjautua sisään.");
                    naytaJSP("login.jsp", request, response);
                    return;
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Kirjautuminen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kirjautuminen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
