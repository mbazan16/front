<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Inicio</title>
<link href = "css/estilo.css" rel = "stylesheet" type = "text/css">
</head>
<body id="portada">
  <div>
		<h2>${nombreElementos}</h2>
		<c:forEach items="${elementos}" var="huerto">
			<div class="gallery">
				<a href="javascript:verPlanta(${huerto.id})"> <img class="gallery"
					src="img/planta.jpg" alt="${huerto.nombre}">
				</a>
				<div class="desc">${huerto.nombre}
					<img src="img/minus.png" alt="Eliminar Huerto"
						onClick="javascript:eliminar(${huerto.id})">
				</div>
			</div>
		</c:forEach>
	</div>  
  <c:if test="${error!=null}">
		<div class="error">${error}</div>
</c:if>
</body>
</html>