<%--
  Created by IntelliJ IDEA.
  User: GrupoUTP
  Date: 05/11/2016
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List Countries</title>
</head>
<body>
    <jsp:useBean id="service" class="pe.edu.utp.hrregionswebapp.HRService"/>
    <p>Country Name</p>
    <c:forEach var="country" items="${service.countriesWithRegion}">
        <p><c:out value="${country.name}"/> | <c:out value="${country.region.name}"/> </p>
    </c:forEach>

</body>
</html>
