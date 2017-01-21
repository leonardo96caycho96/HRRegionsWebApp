<%--
  Created by IntelliJ IDEA.
  User: GrupoUTP
  Date: 05/11/2016
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List Regions</title>
</head>
<body>
<jsp:useBean id="service" class="pe.edu.utp.hrregionswebapp.HRService"/>
<p>Regions count:
    <jsp:getProperty name="service" property="regionsCount"/>
</p>
<p>Region Name</p>
<c:forEach var="region" items="${service.regions}">
    <p>
        <c:out value="${region.name}"/>
        <a href="regions?action=edit&id=<c:out value="${region.id}"/>">Edit</a>
        <a href="regions?action=delete&id=<c:out value="${region.id}"/>">Delete</a>
    </p>
</c:forEach>
<a href="regions?action=new">New Region</a>
</body>
</html>
