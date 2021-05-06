<%--
  Created by IntelliJ IDEA.
  User: macmini2
  Date: 09/04/2020
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Zmiana urlopu</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>


        <h1>Zmień dane ośrodka</h1>

        <form action="UserServlet" method="get">
            <input type="hidden" name="command" value="CHANGE"/>
            <input type="hidden" name="id" value="${VACATION.id}"/>
            <input type="hidden" name="id_employee" value="${VACATION.id_employee}"/>
            <input type="hidden" name="state" value="${VACATION.state}"/>
            <div class="form-group">
                Data początku
                <input type="date"name="start_date" value="${VACATION.start_date}"/>
            </div>
            <div class="form-group">
                Data końca
                <input type="date"name="end_date" value="${VACATION.end_date}"/>
            </div>
            <button type="submit">Zmień dane</button>
        </form>

</body>
</html>