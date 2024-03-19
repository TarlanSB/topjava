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
<h3><a href="meals?action=add">Add Meal</a></h3>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>

    <c:forEach var="mealTo" items="${meals}">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>

    <tr style="color: ${mealTo.excess ? 'red' : 'green'}">
        <td>
            <fmt:parseDate value="${mealTo.dateTime}" var="parsedDateTime" pattern="yyyy-MM-dd'T'HH:mm"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" type="both" value="${parsedDateTime}"/>
        </td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td><a href="meals?id=${mealTo.id}&action=edit">Update</a></td>
        <td><a href="meals?id=${mealTo.id}&action=delete">Delete</a></td>

    </tr>
    </c:forEach>
</body>
</html>
