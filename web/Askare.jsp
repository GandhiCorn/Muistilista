<%-- 
    Document   : Askare
    Created on : 12-Aug-2015, 17:11:47
    Author     : fuksi
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Ossin muistilista!"> 




    <div class="bs-example">



        <c:forEach var="askare" items="${askareet}"> 
            <h1>Askare: <c:out value="${askare.nimi}"/></h1>                
            <p>Tärkeys: <c:out value="${askare.tarkeys}"/></p>                        
            <p>Luokka: <c:out value="${askare.luokkaid}"/></p>
            <form action="NewAskareServlet?id=${askare.id}" method="POST">
                <div class="btn-group">
                    <button type="submit" class="btn btn-default">Muokkaa</button>                
                </div>
            </form>
        </c:forEach>




    </div>
</t:pohja>