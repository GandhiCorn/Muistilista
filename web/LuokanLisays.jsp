<%-- 
    Document   : LuokanLisays
    Created on : 18-Aug-2015, 12:38:12
    Author     : fuksi
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Muistilista"> 




    <div class="bs-example">

        <form action="LuokanLisaaminen" method="POST">
            <div class="input-group input-group-lg">                
                <h2>Luokka: </h2>
                <input type="text" name="nimi" value="<c:out value="${nimi}"/>" class="form-control" placeholder="Luokan nimi">
            </div>

            <div class="btn-group-vertical">
                <button type="submit" class="btn btn-default">Tallenna</button>
            </div>
        </form>





    </div>
</t:pohja>