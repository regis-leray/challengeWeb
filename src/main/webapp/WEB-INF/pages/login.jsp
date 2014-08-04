<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>


    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
</head>
<body>

<div class="container">

<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert"><strong>Error : </strong>${error}</div>
</c:if>

    <c:if test="${not empty identifier}">
        <div class="alert alert-success" role="alert"><strong>Successful authentification !!</strong> <br /> Open id Identifier : ${identifier.identifier}</div>
    </c:if>


    <h1>OpenId login</h1>

    <div style="margin-left: 50px; margin-top: 40px; height: 60px;">
        <form action="/openid/login?openid=https://www.appdirect.com/openid/id" method="post"><input
                class="appdirect openid_large_btn"
                type="image" value="Login with AppDirect"/></form>
    </div>
</div>

</body>
</html>
