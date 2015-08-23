
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Muistilista"> 
    <h1>Tervetuloa ${kayttaja}, sinulla on ${listanKoko} tehtävää listallasi.</h1>  

    <c:if test="${ilmoitus != null}">
        <div class="alert alert-info">${ilmoitus}</div>
    </c:if>
    <div class="bs-example">
        <table class="table">
            <thead>
            <tr>
                <th>Askare</th>
                <th>Tärkeysaste</th>
                <th>Luokka</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="askare" items="${askareet}"> 
                    <tr>
                        <td><a href="Muokkaus?askareenId=${askare.askareenId}&nimi=${askare.nimi}
                               &tarkeys=${askare.tarkeys}&luokka=${askare.luokka}"><c:out value="${askare.nimi}"/></a></td>
                        <td><c:out value="${askare.tarkeys}"/></td>                        
                        <td><c:out value="${askare.luokka}"/></td>
                        <td><a href="AskareenPoistaminen?id=${askare.askareenId}"<button type="submit" class="btn btn-xs btn-default"><span class="col-md-offset-0 col-md-1"></span> POISTA</button></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <form action="AskareenLisays.jsp?" method="POST">
            <button type="submit" class="btn btn-default">Luo uusi askare</button>
        </form>
    </div>





</t:pohja>