<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Oferta</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>



<h1>Twoje wakacje</h1>

<h2>
    ${USER_INFO}
</h2>


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
id pracownika wynosi:${VACATION_LIST[0].id_employee}
<br>
<a href="add_vacation.jsp?id_employee=${VACATION_LIST[0].id_employee}">Dodaj urlop</a>


</body>
</html>