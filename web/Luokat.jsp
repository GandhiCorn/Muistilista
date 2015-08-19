
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Muistilista"> 

    <c:if test="${ilmoitus != null}">
        <div class="alert alert-info">${ilmoitus}</div>
    </c:if>
    <div class="bs-example">
        <table class="table">
            <thead>
                <tr>
                    <th>Luokan nimi</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="luokka" items="${luokat}"> 
                    <tr>
                        <td><a href="LuokanMuokkaus?luokanId=${luokka.luokkaId}&nimi=${luokka.nimi}"><c:out value="${luokka.nimi}"/></a></td>
                        <td><a href="LuokanPoistaminen?luokanId=${luokka.luokkaId}&nimi=${luokka.nimi}"<button type="submit" class="btn btn-xs btn-default"><span class="col-md-offset-0 col-md-1"></span> POISTA</button></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <form action="LuokanLisays.jsp?" method="POST">
            <button type="submit" class="btn btn-default">Luo uusi luokka</button>
        </form>
    </div>





</t:pohja>