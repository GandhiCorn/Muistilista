<%-- 
    Document   : pohja
    Created on : 03-Aug-2015, 16:27:13
    Author     : fuksi
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8" trimDirectiveWhitespaces = "true" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href="../css/bootstrap-theme.css" rel="stylesheet">
        <link href="../css/main.css" rel="stylesheet">
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>
        <ul class="nav nav-tabs">
            <li class="active"><a href="etusivu.html">Etusivu</a></li>
            <li><a href="luokat.html">Luokat</a></li>
            <li><a href="kirjautuminen.html">Kirjaudu ulos</a></li>
        </ul>
        <div class="container">
            <h1>Muistilista</h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Askare</th>
                        <th>Luokka</th>
                        <th></th>

                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Imuroi</td>
                        <td>Koti</td>
                        <td>1</td>

                        <td><a href="luokkatietosivu.html"<button type="submit" class="btn btn-xs btn-default"><span class="col-md-offset-0 col-md-1"></span> Muokkaa</button></a></td>     
                    </tr>
                    <tr>
                        <td>Käy kaupassa</td>
                        <td>Koti</td>
                        <td>2</td>

                        <td><a href="luokkatietosivu.html"<button type="submit" class="btn btn-xs btn-default"><span class="col-md-offset-0 col-md-1"></span> Muokkaa</button></a></td>
                    </tr>
                    <tr>
                        <td>Lenkille</td>
                        <td>Harrastukset</td> 
                        <td>2</td>

                        <td><a href="luokkatietosivu.html"<button type="submit" class="btn btn-xs btn-default"><span class="col-md-offset-0 col-md-1"></span> Muokkaa</button></a></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>

</html>