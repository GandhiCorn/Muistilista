<%-- 
    Document   : pohja
    Created on : Sep 21, 2014, 1:10:08 AM
    Author     : Jyri
--%>
<%@tag description="Esimerkistä muokattu pohja muistilistalle" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/maincss.css" rel="stylesheet">        
    </head>
    <body>
        <div class="ylapalkki">
            <h1>Muistilista</h1>
        </div>
        <ul class="nav nav-tabs" role="tablist">
            <li><a href="IndexServlet">Etusivu</a></li>           
            <li><a href="ClassServlet">Hallinnoi luokkia</a></li>
            <li><a href="Logout">Kirjaudu ulos</a></li>
        </ul>

        <div class="container">
            <c:if test="${pageError == 'Kirjautumista ei tunnisteta'}">
                <c:redirect url="login.jsp"/>
            </c:if>

            <c:if test="${pageError != null}">
                <div class="alert alert-danger">${pageError}</div>
            </c:if>
            <jsp:doBody/>
        </div>
    </body>
</html>