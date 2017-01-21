<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GrupoUTP
  Date: 11/11/2016
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<form action="regions" method="post">
    <input type="text" name="id" value="${region.id}" <c:out value="${action == 'edit' ? 'readonly=\"readonly\"' : ''}"/> />
    <input type="text" name="name" value="${region.name}"/>
    <input type="hidden" name="action" value="<c:out value="${action == 'edit' ? 'update' : 'create'}"/>"/>
    <input type="submit"/>
</form>
<a href="regions?action=index">Return to Regions List</a>