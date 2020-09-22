<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<script>
	function visualizar() {
		document.getElementById("panelView").style.display = "block";
		document.getElementById("imgPlus").style.display = "block";
		document.getElementById("panelModificarForm").style.display = "none";
		document.getElementById("panelCrearForm").style.display = "none";
	}

	function modificar() {
		if (confirm("�Desea modificar este elemento?")) {

			document.getElementById("formVivero").accion.value = "MODIFICAR";
			document.getElementById("formVivero").submit();
			document.getElementById("panelView").style.display = "block";
			document.getElementById("imgPlus").style.display = "block";
			document.getElementById("panelModificarForm").style.display = "none";
		}
	}
	function aModificar() {
		document.getElementById("panelView").style.display = "none";
		document.getElementById("imgPlus").style.display = "none";
		document.getElementById("panelModificarForm").style.display = "block";
	}

	function irCrear(){
		document.getElementById("imgPlus").style.display = "none";
		document.getElementById("panelCrearForm").style.display = "block";
	}
	
	function eliminar(id){
		if(confirm("�Desea eliminar el huerto?")){
		document.getElementById("formHuerto").id.value = id;
		document.getElementById("formHuerto").accion.value = "ELIMINAR"; 
		
		document.getElementById("formHuerto").submit();
		}
	}
	function verHuerto(id){
		document.getElementById("formHuerto").id.value = id;
		document.getElementById("formHuerto").accion.value = "VER"; 
		
		document.getElementById("formHuerto").submit();
	}
</script>
<head>
<meta charset="ISO-8859-1">
<title>${titulo}</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body class="vivero">
	<div class="topright">
		<a href="./viveros" class="w">�</a>
	</div>
	<div id="panelView" class="card">
		<h1 class="verde">${titulo}
			${elemento.nombre} <a href="javascript:aModificar()"
				class="w  nobutton">?</a>
		</h1>
	</div>
	<div id="panelModificarForm" class="card" style="display: none">
		<h1 class="verde">${titulo}${elemento.nombre}</h1>
		<form method="post" action="./vivero" id="formVivero">
			<input hidden="true" type="text" name="elemento" value="VIVERO" />
			<input hidden="true" type="text" name="accion" value="MODIFICAR" />
			<input hidden="true" type="text" name="id" value="${elemento.id}" />
			<label for="nombre">Identificador:</label> ${elemento.id} <label
				for="nombre">Nombre:</label> <input id="nombre" type="text"
				name="nombre" value="${elemento.nombre}" required
				pattern="[A-Za-z]+.{3,25}"
				title="El nombre tiene que estar formado por letras y m�s de 4 caracteress" /><br>
			<input type="submit" value="Modificar"> <input type="button"
				value="Volver" onClick="javascript:visualizar()">
		</form>
	</div>
	<c:if test="${error!=null}">
		<div class="error">${error}</div>
	</c:if>
	<div>
		<h2>${nombreElementos}</h2>
		<c:forEach items="${elemento.huertos}" var="huerto">
			<div class="gallery">
				<a href="javascript:verHuerto(${huerto.id})"> <img class="gallery"
					src="img/huerto.jpg" alt="${huerto.nombre}">
				</a>
				<div class="desc">${huerto.nombre}
					<img src="img/minus.png" alt="Eliminar Huerto"
						onClick="javascript:eliminar(${huerto.id})">
				</div>
			</div>
		</c:forEach>
	</div>
	<div>
		<img id="imgPlus" src="img/plus.png" alt="Crear Huerto"
			onClick="javascript:irCrear()">
	</div>
	<div id="panelCrearForm" class="gallery" style="display: none">
		<h2>Nuevo Huerto</h2>
		<form method="post" action="./huerto" id="formHuerto">
			<input hidden="true" type="text" name="accion" value="CREAR" />
			<input hidden="true" type="text" name="elemento" value="HUERTO" />
			<input hidden="true" type="text" name="idVivero" value="${elemento.id}" />
		   <input hidden="true" type="text" name="id" value="${proximoId}" />
		    <label
				for="nombre">Identificador:</label> ${proximoId} <br> <label
				for="nombre">Nombre:</label> <input id="nombre" type="text"
				name="nombre" value="${elemento.nombre}" required
				pattern="[A-Za-z]+.{3,25}"
				title="El nombre tiene que estar formado por letras y m�s de 4 caracteress" /><br>
			<input type="submit" value="Crear"> <input type="button"
				value="Volver" onClick="javascript:visualizar()">
		</form>
	</div>
</body>
</html>