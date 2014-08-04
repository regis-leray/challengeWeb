<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Accounts list</title>

        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

    </head>

    <body>


    <div class="container">
        <h1>Subscriptions</h1>

        <hr>
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>status</th>
                <th>Edition Code</th>
                <th>Pricing Duration</th>
                <th>Subscription - Items : quantity (unit)</th>

                <th>Users assign</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${subscriptions}" var="sub">
                <tr>
                    <td>${ sub.id } </td>
                    <td>${ sub.status } </td>
                    <td>${ sub.order.editionCode }</td>
                    <td>${ sub.order.pricingDuration } </td>
                    <td><c:forEach items="${sub.order.items}" var="item"> ${ item.quantity } (${ item.unit }) , </c:forEach></td>
                    <td><c:forEach items="${sub.users}" var="user"> ${ user.uuid } (${ user.email }) , </c:forEach></td>
               </tr>
            </c:forEach>
            </tbody>
        </table>



    </div>



	</body>
</html>