/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Muistilista.Servlets;

import Muistilista.Models.Kayttaja;
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

/**
 *
 * @author fuksi
 */
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

            if (nappi == null || nappi.equals("login")) {
                /* Näytetään pelkkä lomake */
                if (kayttaja == null || salasana == null) {
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                //Tarkistetaan että vaaditut kentät on täytetty:
                if (kayttaja == null || kayttaja.equals("")) {
                    asetaVirhe("Kirjautuminen epäonnistui! Et antanut käyttäjätunnusta.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }

                /* Välitetään näkymille tieto siitä, mikä tunnus yritti kirjautumista */
                request.setAttribute("kayttaja", kayttaja);

                if (salasana == null || salasana.equals("")) {
                    asetaVirhe("Kirjautuminen epäonnistui! Et antanut salasanaa.", request);
                    naytaJSP("login.jsp", request, response);
                    return;
                }
                HttpSession session = request.getSession();

                Kayttaja client = Kayttaja.etsiKayttajaTunnuksilla(kayttaja, salasana);

                if (kayttaja == null) {
                    asetaVirhe("Kirjautuminen epäonnistui! Käyttäjää ei löytynyt järjestelmästä", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                } else {
                    //Tallennetaan istuntoon käyttäjäolio                
                    session.setAttribute("kirjautunut", kayttaja);
                    //Uudelleenohjaus
                    response.sendRedirect("IndexServlet");
                    return;
                }

                //Uuden käyttäjän luonti
            } else if (nappi.equals("newuser")) {
                /* Näytetään pelkkä lomake */
                if (kayttaja == null || salasana == null) {
                    naytaJSP("Login.jsp", request, response);
                    return;
                }

                if (kayttaja.equals("")) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Et antanut käyttäjätunnusta.", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                }

                if (kayttaja.length() > 20) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Käyttäjätunnuksen maksimipituus on 20 merkkiä", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                }

                /* Välitetään näkymille tieto siitä, mikä tunnus yritti kirjautumista */
                request.setAttribute("kayttaja", kayttaja);

                if (salasana.equals("")) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Et antanut salasanaa.", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                }

                if (salasana.length() > 128) {
                    asetaVirhe("Uuden käyttäjän luonti epäonnistui! Salasanan maksimipituus on 128 merkkiä.", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                }

                /* Tarkistetaan onko parametrina saatu oikeat tunnukset */
                
                boolean kayttajaLoytyyJo = Kayttaja.etsiKayttaja(kayttaja);
                if (kayttajaLoytyyJo) {
                    asetaVirhe("Käyttäjänimi on varattu. Kokeile toista.", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                } else {
                    Kayttaja.luoUusiKayttaja(kayttaja, salasana);
                    asetaVirhe("Käyttäjä luotu onnistuneesti. Voit nyt kirjautua sisään.", request);
                    naytaJSP("Login.jsp", request, response);
                    return;
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Kirjautuminen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kirjautuminen.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Uuden käyttä  jän luonti
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
