<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Muistilista"> 




    <div class="bs-example">

        <form action="AskareenPaivittaminen" method="POST">
            <div class="input-group input-group-lg">                
                <h2>Askare: </h2>
                <input type="text" name="nimi" value="<c:out value="${nimi}"/>" class="form-control" placeholder="Askareen nimi">
            </div>
            <div class="input-group input-group-lg">                
                <h2>Tärkeysarvo: </h2>
                <input type="text" name="tarkeys" value="<c:out value="${tarkeys}"/>" class="form-control" placeholder="Tärkeysarvo">
            </div>
            <div class="input-group input-group-lg">                
                <h2>Luokka: </h2>
                <input type="text" name="luokka" value="<c:out value="${luokka}"/>" class="form-control" placeholder="Luokan nimi">
            </div>
            <%--<br/>
            <br/>
            <label>Luokka</label>
            <select name="luokka">
                <c:forEach var="luokka" items="${luokat}">
                    <option value="${luokka.luokkaId}">${luokka.nimi}</option>
                </c:forEach>
            </select>--%>

            <input type="hidden" name="askareenId" value="${askareenId}">

            <div class="btn-group-vertical">
                <button type="submit" class="btn btn-default">Tallenna</button>
            </div>
        </form>





    </div>
</t:pohja>