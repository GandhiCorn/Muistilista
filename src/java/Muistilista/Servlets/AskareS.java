package Muistilista.Servlets;

import Muistilista.Models.*;
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

/**
 *
 * @author tuisk
 */
public class AskareS extends ToistuvaKoodi {

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

        String askareID = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(askareID);
        } catch (Exception e) {
            id = -1;
        }
        if (tarkistaKirjautuminen(request)) {

            //Haetaan askareen tarkat tiedot
            request.setAttribute("test", id);
            List<Askare> askareet = null;

            boolean lopeta = false;
            try {
                askareet = Askare.etsiAskare(id);
                Askare askare = askareet.get(0);
                if (!askare.getKayttaja().equals(tarkistaKayttajanimi(request))) {
                    asetaVirhe("Tämä askare ei kuulu sinun käyttäjälle", request);
                    naytaJSP("Askare.jsp", request, response);
                    lopeta = true;
                }

            } catch (NamingException ex) {
                Logger.getLogger(AskareS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AskareS.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!lopeta) {
                request.setAttribute("askareet", askareet);
                naytaJSP("Askare.jsp", request, response);
            }

        } else {
            asetaVirhe("NOT LOGGED IN", request);
            naytaJSP("Index.jsp", request, response);
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
