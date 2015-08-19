/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Muistilista.Servlets;

import Muistilista.Models.Kayttaja;
import Muistilista.Models.Luokka;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
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
public class LuokanLisaaminen extends ToistuvaKoodi {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");
        String kayttaja = kirjautunut.getKayttajatunnus();

        Luokka uusiLuokka = new Luokka();
        uusiLuokka.setKayttaja(kayttaja);
        uusiLuokka.setNimi(request.getParameter("nimi"));

        if (uusiLuokka.onkoKelvollinen()) {
            try {
                uusiLuokka.lisaaKantaan();
                session.setAttribute("ilmoitus", "Luokka lisätty onnistuneesti");
                session.setAttribute("luokat", Luokka.haeKaikki(kayttaja));
                naytaJSP("LuokanLisays.jsp", request, response);
            } catch (NamingException ex) {
                Logger.getLogger(LuokanLisaaminen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LuokanLisaaminen.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Collection<String> virheet = uusiLuokka.getVirheet();
            request.setAttribute("virheet", virheet);
            try {
                request.setAttribute("luokat", Luokka.haeKaikki(kayttaja));
            } catch (NamingException ex) {
                Logger.getLogger(LuokanLisaaminen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LuokanLisaaminen.class.getName()).log(Level.SEVERE, null, ex);
            }
            asetaVirhe(virheet, request);
            naytaJSP("LuokanLisays.jsp", request, response);
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
