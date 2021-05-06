<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Dodawanie Urlopu</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">

</head>
<body>

<h1>Wpisz dane nowego urlopu</h1>
<form action="UserServlet" method="get">
    <input type="hidden" name="command" value="ADD">
    <input type="hidden" name="id_employee" value="<%=request.getParameter("id_employee")%>">
    Początek Urlopu
    <input type="date" name="start_date"/>

    Koniec Urlopu
    <input type="date" name="end_date"/>
    <button type="submit">Dodaj</button>
</form>


<div class="row form-group"></div>
<div class="row form-group"></div>

<form action="UserServlet">
    <input type="hidden" name="id_employee" value="<%=request.getParameter("id_employee")%>">
    <button type="submit">Wróć do zestawienia</button>
</form>

</body>
</html>
