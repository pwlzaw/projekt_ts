<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Urlopy</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>

<h1>Twoje urlopy</h1>

<h2>
    ${USER_INFO}
</h2>

<table class="table">

    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Data początku</th>
        <th scope="col">Data końca</th>
        <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="tmpVacation" items="${VACATION_LIST}">

        <c:url var="updateLink" value="UserServlet">
            <c:param name="command" value="LOAD"></c:param>
            <c:param name="vacationID" value="${tmpVacation.id}"></c:param>
        </c:url>

        <c:url var="deleteLink" value="UserServlet">
            <c:param name="command" value="DELETE"></c:param>
            <c:param name="vacationID" value="${tmpVacation.id}"></c:param>
            <c:param name="id_employee" value="${tmpVacation.id_employee}"></c:param>
        </c:url>


        <tr>
            <th scope="row">${tmpVacation.id}</th>
            <td>${tmpVacation.start_date}</td>
            <td>${tmpVacation.end_date}</td>
            <td>${tmpVacation.state}</td>
            <td><a href="${updateLink}">
                <button type="button">Zmień urlop</button>
            </a>
                <a href="${deleteLink}"
                   onclick="if(!(confirm('Czy na pewno chcesz usunąć ten Ośrodek?'))) return false">
                    <button type="button">Usuń urlop</button>
                </a></td>
        </tr>

    </c:forEach>
    </tbody>
</table>

<br>
<p>
<a href="add_vacation.jsp?id_employee=${EmployeeID}" class="btn btn-primary">Dodaj urlop</a>
</p>
<br>
<a href="index.html"  role="button">Wróć do strony głównej</a>

</body>
</html>