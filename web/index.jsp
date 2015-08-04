<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="MUISTILISTA"> 
    <h1>Tervetuloa ${kayttaja}, sinulla on ${listSize} tehtävää.</h1>  


    <div class="bs-example">
        <table class="table">
            <thead>
                <tr>
                    <th>Askare</th>
                    <th>Tarkeys</th>
                    <th>Luokka</th>
                </tr>
            </thead>
            <tbody>

                <c:forEach var="askare" items="${askareet}"> 
                    <tr>
                        <td><a href="AskareServlet?id=${askare.id}"><c:out value="${askare.nimi}"/></a></td>
                        <!--<td><c:out value="${askare.nimi}"/></td>-->
                        <td><c:out value="${askare.tarkeys}"/></td>                        
                        <td><c:out value="${askare.luokkaid}"/></td>
                        <td><a href="AskareServlet?id=${askare.id}&poista=1"><c:out value="POISTA"/></a></td>
                    </tr>
                </c:forEach>




            </tbody>
        </table>
        <form action="NewAskareServlet?id=-1" method="POST">
            <button type="submit" class="btn btn-default">Luo uusi askare</button>
        </form>
    </div>





</t:pohja>