<%-- 
    Document   : askare
    Created on : 12-Aug-2015, 16:56:36
    Author     : fuksi
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Muistilista"> 




    <div class="bs-example">

        <form action="AskareenLisaaminen" method="POST">
            <div class="input-group input-group-lg">                
                <h2>Askare: </h2>
                <input type="text" name="nimi" value="<c:out value="${nimi}"/>" class="form-control" placeholder="Askareen nimi">
            </div>
            <div class="input-group input-group-lg">                
                <h2>Tärkeysarvo: </h2>
                <input type="text" name="tarkeys" value="<c:out value="${tarkeys}"/>" class="form-control" placeholder="Tärkeysarvo">
            </div>
            
            <label><h2>Luokka:</h2</label>
            <select name="luokka">
                <option value="tyhja">- - -</option>
                <c:forEach var="luokka" items="${luokat}">
                    <option value="${luokka.nimi}"> ${luokka.nimi}</option>
                </c:forEach>
            </select>

            <div class="btn-group-vertical">
                <button type="submit" class="btn btn-default">Luo askare</button>
            </div>
        </form>





    </div>
</t:pohja>