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
<h3><a href="add">Add Meal</a></h3>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>


    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal"/>

    <tr style="color: ${meal.excess ? 'red' : 'green'}">
        <td>
            <fmt:parseDate value="${meal.dateTime}" var="parsedDateTime" pattern="yyyy-MM-dd'T'HH:mm"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" type="both" value="${parsedDateTime}"/>
        </td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="meals?id=${meal.id}&action=edit">Update</a></td>
        <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>

    </tr>
    </c:forEach>
</body>
</html>