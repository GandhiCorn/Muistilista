<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>MUISTILISTA</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/maincss.css" rel="stylesheet">
    </head>
    <body>
        <div class = "keskitys">
            <h1>MUISTILISTA</h1>

            <c:if test="${pageError != null}">
                <div class="alert alert-danger">${pageError}</div>
            </c:if>

            <form action="ClientServlet" method="POST">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon"></span>
                    <input type="text" name="username" value="${kayttaja}" class="form-control" placeholder="Käyttäjänimi">
                </div>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon"></span>
                    <input type="password" name="password" class="form-control" placeholder="*******">
                </div>
                <div class="btn-group">
                    <button name="subject" type="submit" value="login" class="btn btn-default">Kirjaudu</button>
                    <button name="subject" type="submit" value="newuser" class="btn btn-default">Luo uusi käyttäjä</button>
                </div>
            </form>
        </div>
    </body>
</html>