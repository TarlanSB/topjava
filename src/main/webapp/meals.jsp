<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>

     <c:forEach var="meal" items="${list_mealsTo}">
    <tr style="color: ${meal.excess == true ? 'red' : 'green'}">
        <td>
            <fmt:parseDate value="${meal.dateTime}" var="parsedDateTime" pattern="yyyy-MM-dd'T'HH:mm"/>
            <fmt:formatDate type="both" value="${parsedDateTime}"/>
        </td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td></td>
        <td></td>
    </tr>
    </c:forEach>
</body>
</html>
