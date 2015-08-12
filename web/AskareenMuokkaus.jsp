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
            <div class="input-group input-group-lg">                
                <h2>Luokka: </h2>
                <input type="text" name="luokka" value="<c:out value="${luokka}"/>" class="form-control" placeholder="Luokan nimi">
            </div>

            <input type="hidden" name="ID" value="${id}">
            <input type="hidden" name="kayttaja" value="${kayttaja}">
            <div class="btn-group-vertical">
                <button type="submit" class="btn btn-default">Tallenna</button>
            </div>
        </form>





    </div>
</t:pohja>