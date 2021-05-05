<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Oferta</title>
</head>

<body>



<h1>Twoje wakacje</h1>


<table class="table table-striped">

    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Data początku</th>
        <th scope="col">Data końca</th>
        <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="tmpVacation" items="${VACATIONS_LIST}">

        <tr>
            <th scope="row">${tmpVacation.id}</th>
            <td>${tmpVacation.model}</td>
            <td>${tmpVacation.make}</td>
            <td>${tmpVacation.price}</td>
        </tr>


    </c:forEach>
    </tbody>
</table>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="row">
    <div class="container-fluid">

        <div class="col-sm-9">
            <a href="index.html" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do strony głównej</a>
        </div>
    </div>
</div>


</body>
</html>