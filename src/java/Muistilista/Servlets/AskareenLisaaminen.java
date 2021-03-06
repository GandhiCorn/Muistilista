package Muistilista.Servlets;

import Muistilista.Models.Askare;
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

//Servlet askareen lisäämistä tietokantaa varten
public class AskareenLisaaminen extends ToistuvaKoodi {

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
        String luokka = request.getParameter("luokka");

        //Alustetaan askare olio näkymältä saaduilla parametreilla
        try {
            Askare uusiAskare = new Askare();
            uusiAskare.setNimi2(request.getParameter("nimi"));
            uusiAskare.setTarkeys(request.getParameter("tarkeys"));
            uusiAskare.setKayttaja(kayttaja);
            if (!luokka.equals("tyhja")) {
                uusiAskare.setLuokka2(request.getParameter("luokka"));
            }
            
            //Jos alustaminen ei tuottanut virheitä, niin kutsutaan metodia lisäämään olio kantaan
            if (uusiAskare.onkoKelvollinen()) {
                uusiAskare.lisaaKantaan();
                session.setAttribute("ilmoitus", "Askare lisätty onnistuneesti.");
                response.sendRedirect("Index");

                //Muuten asetetaan virheet, askareen tiedot ja haetaan luokat sekä asetaan virheviesti
                //ja näytetään näkymä uudestaan
            } else {
                Collection<String> virheet = uusiAskare.getVirheet();
                request.setAttribute("virheet", virheet);
                request.setAttribute("askare", uusiAskare);
                request.setAttribute("luokat", Luokka.haeKaikki(kayttaja));
                asetaVirhe(virheet, request);
                naytaJSP("AskareenLisays.jsp", request, response);
            }

        } catch (NamingException ex) {
            Logger.getLogger(AskareenLisaaminen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AskareenLisaaminen.class.getName()).log(Level.SEVERE, null, ex);
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
