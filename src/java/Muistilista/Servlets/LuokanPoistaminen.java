package Muistilista.Servlets;

import Muistilista.Models.Askare;
import Muistilista.Models.Kayttaja;
import Muistilista.Models.Luokka;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
public class LuokanPoistaminen extends ToistuvaKoodi {

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
        
        //haetaan käyttäjä
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");
        String kayttaja = kirjautunut.getKayttajatunnus();
        
        //haetaan parametrin id ja parsetaan se
        String luokanId = request.getParameter("luokanId");
        int id;
        try {
            id = Integer.parseInt(luokanId);
        } catch (Exception e) {
            id = -1;
        }

        //Ensin poistetaan kaikilta luokan askareilta luokkatieto jottei ne poistu luokan mukana
        //jonka jälkeen poistetaan itse luokka
        try {
            String luokanNimi = request.getParameter("nimi");
            Askare.poistaAskareenLuokka(luokanNimi);
            Luokka.poistaLuokka(id);
            request.setAttribute("luokat", Luokka.haeKaikki(kayttaja));

        } catch (NamingException ex) {
            Logger.getLogger(AskareenPoistaminen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AskareenPoistaminen.class.getName()).log(Level.SEVERE, null, ex);
        }
        //näytetään luokkanäkymän JSP
        naytaJSP("Luokat.jsp", request, response);
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
