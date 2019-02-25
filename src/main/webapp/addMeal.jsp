<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Yana
  Date: 21.02.2019
  Time: 0:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">
    Meal ID     : <input type="text"
                         readonly="readonly"
                         name="id"
                       value=${meal.id}>

    <br />
    Date        : <input type="datetime-local"
                         name="dateTime"
                  value=${meal.dateTime}>

    <br />
    Description : <input type="text"
                         name="description"
                  value=${meal.description} >
    <br />
    Calories    : <input type="text"
                         name="calories"
                  value=${meal.calories}>
    <br />

    <input type="submit" value="Submit" />
</form>






</body>
</html>
