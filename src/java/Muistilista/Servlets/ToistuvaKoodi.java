
package Muistilista.Servlets;

import Muistilista.Models.Kayttaja;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Toistuvaa koodia sisältävä luokka jonka muut servletit perii
public class ToistuvaKoodi extends HttpServlet {

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
    }

    //pyydetään näyttämään JSP
    protected void naytaJSP(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }

    //asetetaan virhe
    protected void asetaVirhe(String virhe, HttpServletRequest request) {
        request.setAttribute("pageError", virhe);
    }

    //asetetaan useampi virhe kerralla
    protected void asetaVirhe(Collection<String> virheet, HttpServletRequest request) {
        for(String virhe  : virheet) {
            request.setAttribute("pageError", virhe);
        }
    }

    //tarkistetaan onko käyttäjä kirjautunut sisään
    protected boolean tarkistaKirjautuminen(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");
        return kirjautunut != null;
    }

    //tarkistetaan vastaako käyttäjänimi kirjautuneen käyttäjänimeä
    protected String tarkistaKayttajanimi(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");
        return kirjautunut.getKayttajatunnus();
    }

    //kirjaudutaan ulos 
    protected void kirjauduUlos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("kirjautunut");
    }

    //haetaan ilmoitus jos sellainen on olemassa
    protected void haeIlmoitus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String ilmoitus = (String) session.getAttribute("ilmoitus");

        if (ilmoitus != null) {
            // Samalla kun viesti haetaan, se poistetaan istunnosta,
            // ettei se näkyisi myöhemmin jollain toisella sivulla uudestaan.
            session.removeAttribute("ilmoitus");

            request.setAttribute("ilmoitus", ilmoitus);
        }
    }

    //haetaan kayttaja
    protected String haeKayttaja(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");

        return kirjautunut.getKayttajatunnus();
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
