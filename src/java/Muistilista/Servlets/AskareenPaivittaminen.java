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

/**
 *
 * @author tuisk
 */
public class AskareenPaivittaminen extends ToistuvaKoodi {

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

        String askareenId = request.getParameter("id");

        HttpSession session = request.getSession();
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");
        String kayttaja = kirjautunut.getKayttajatunnus();

        int id;
        try {
            id = Integer.parseInt(askareenId);
        } catch (Exception e) {
            id = -1;
        }

        try {
            Askare uusiAskare = new Askare();
            uusiAskare.setNimi1(request.getParameter("nimi"));
            uusiAskare.setTarkeys(request.getParameter("tarkeys"));
            uusiAskare.setLuokka1(request.getParameter("luokka"));
            uusiAskare.setKayttaja(kayttaja);
            uusiAskare.setAskareenId(id);

            if (uusiAskare.onkoKelvollinen()) {
                uusiAskare.paivitaAskare();
                session.setAttribute("ilmoitus", "Askare muokattu onnistuneesti." + askareenId );
                response.sendRedirect("Index");
                
            } else {
                Collection<String> virheet = uusiAskare.getVirheet();
                request.setAttribute("virheet", virheet);
                request.setAttribute("askare", uusiAskare);
                request.setAttribute("luokat", Luokka.haeKaikki(kayttaja));
                asetaVirhe(virheet.toString(), request);
                naytaJSP("AskareenPaivittaminen.jsp", request, response);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AskareenPaivittaminen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AskareenPaivittaminen.class.getName()).log(Level.SEVERE, null, ex);
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
