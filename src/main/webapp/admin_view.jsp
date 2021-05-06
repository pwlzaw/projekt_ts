<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Admin</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>


<h1>Urlopy do rozpatrzenia</h1>


<table class="table">

    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Id pracownika</th>
        <th scope="col">Data początku</th>
        <th scope="col">Data końca</th>
        <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="tmpVacation" items="${VACATIONS_LIST}">

        <c:if test="${tmpVacation.state!='waiting change acceptation'}">
            <c:url var="acceptLink" value="AdminServlet">
                <c:param name="command" value="ACCEPT"></c:param>
                <c:param name="id" value="${tmpVacation.id}"></c:param>
                <c:param name="id_employee" value="${tmpVacation.id_employee}"></c:param>
                <c:param name="start_date" value="${tmpVacation.start_date}"></c:param>
                <c:param name="end_date" value="${tmpVacation.end_date}"></c:param>
                <c:param name="state" value="${tmpVacation.state}"></c:param>
            </c:url>

            <c:url var="denyLink" value="AdminServlet">
                <c:param name="command" value="DENY"></c:param>
                <c:param name="id" value="${tmpVacation.id}"></c:param>
                <c:param name="id_employee" value="${tmpVacation.id_employee}"></c:param>
                <c:param name="start_date" value="${tmpVacation.start_date}"></c:param>
                <c:param name="end_date" value="${tmpVacation.end_date}"></c:param>
                <c:param name="state" value="${tmpVacation.state}"></c:param>
            </c:url>
        </c:if>


        <tr>
            <th scope="row">${tmpVacation.id}</th>
            <td>${tmpVacation.id_employee}</td>
            <td>${tmpVacation.start_date}</td>
            <td>${tmpVacation.end_date}</td>
            <td>${tmpVacation.state}</td>

            <c:if test="${tmpVacation.state!='waiting change acceptation'}">
            <td><a href="${acceptLink}">
                <button type="button">Zaakceptuj</button>
            </a>
                <a href="${denyLink}">
                    <button type="button">Odrzuć</button>
                </a></td>
            </c:if>
        </tr>


    </c:forEach>
    </tbody>
</table>


            <a href="index.html"  role="button">Wróć do strony głównej</a>



</body>
</html>
