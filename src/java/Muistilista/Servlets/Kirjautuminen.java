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

            //kerrotaan käyttäjätunnus ettei jää tyhjäksi kerran kun on jotain laitettu
            request.setAttribute("kayttaja", kayttaja);
            //ei salasanaa
            if (salasana == null || salasana.equals("")) {
                asetaVirhe("Kirjautuminen epäonnistui! Et antanut salasanaa.", request);
                naytaJSP("login.jsp", request, response);
                return;
            }

            //tarkistetaan onko käyttäjä olemassa ja välitetään eteenpäin tai annetaan virheilmoitus
            Kayttaja client = Kayttaja.etsiKayttajaTunnuksilla(kayttaja, salasana);
            if (client == null) {
                asetaVirhe("Kirjautuminen epäonnistui! Käyttäjää ei löytynyt järjestelmästä", request);
                naytaJSP("login.jsp", request, response);
            } else {
                //HttpSession session = request.getSession();
                //session.setAttribute("kirjautunut", kayttaja);
                response.sendRedirect("Index");
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
