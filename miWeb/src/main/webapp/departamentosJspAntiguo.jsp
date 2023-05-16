<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.Departamento"  %>
<%@ page import="java.util.List"  %>
<%@ page import="java.util.ArrayList"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<ul><%
List<Departamento> departamentos = (List<Departamento>) request.getAttribute("listado"); 
		for(Departamento departamento:departamentos ) {%>
	<li>
	<%= departamento.getNombre()%>
	</li>
	<%}%>
</ul>

</body>
</html>