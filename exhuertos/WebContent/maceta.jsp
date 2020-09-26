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
		if (confirm("¿Desea modificar este elemento?")) {

			document.getElementById("formMaceta").accion.value = "MODIFICAR";
			document.getElementById("formMaceta").submit();
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
	
	function eliminar(id,idPlanta){
		if(confirm("¿Desea eliminar la planta?")){
		document.getElementById("formPlanta").id.value = id;
		document.getElementById("formPlanta").idPlanta.value = idPlanta;
		document.getElementById("formPlanta").accion.value = "ELIMINAR"; 
		
		document.getElementById("formPlanta").submit();
		}
	}
	function verPlanta(id){
		document.getElementById("formPlanta").id.value = id;
		document.getElementById("formPlanta").accion.value = "VER"; 
		document.getElementById("formPlanta").action = "./planta"; 
		
		document.getElementById("formPlanta").submit();
	}
</script>
<head>
<meta charset="ISO-8859-1">
<title>${titulo}</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body class="maceta">
	<div class="topright">
		<form method="get" action="./huerto" id="formHuerto">
		 	<input hidden="true" type="text" name="accion" value="VER" />
			<input hidden="true" type="text" name="id" value="${elemento.huerto.id}" />
		</form>
		<a href="#" onclick="document.getElementById('formHuerto').submit();" class="w">ç</a>
	</div>
	<div id="panelView" class="card">
		<h1 class="verde">${titulo}
			${elemento.tipoMaceta.descripcion} <a href="javascript:aModificar()"
				class="w  nobutton">?</a>
		</h1>
	</div>
	<div id="panelModificarForm" class="card" style="display: none">
		<h1 class="verde">${titulo}${elemento.tipoMaceta.descripcion}</h1>
		<form method="post" action="./maceta" id="formMaceta">
		   <input hidden="true" type="text" name="accion" value="MODIFICAR" />
			<input hidden="true" type="text" name="id" value="${elemento.id}" />
			<label for="tipoMaceta">Tipo Maceta:</label> 
			<select id="tipoMaceta" name="tipoMaceta" required>
			<c:forEach items="${tiposMacetas}" var="iTipoMaceta">
				<c:choose>
                        <c:when test="${iTipoMaceta.id eq elemento.tipoMaceta.id}">
                            <option value="${iTipoMaceta.id}" selected>${iTipoMaceta.descripcion}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${iTipoMaceta.id}">${iTipoMaceta.descripcion}</option>
                        </c:otherwise>
                    </c:choose>
			</c:forEach>
			</select><br>
			<input type="submit" value="Modificar"> <input type="button"
				value="Volver" onClick="javascript:visualizar()">
		</form>
	</div>
	<c:if test="${error!=null}">
		<div class="error">${error}</div>
	</c:if>
	<div>
		<h2>${nombreElementos}</h2>
		<c:forEach items="${plantas}" var="planta">
			<div class="gallery">
				<a href="javascript:verPlanta(${planta.id})"> <img class="gallery"
					src="img/planta.jpg" alt="${planta.nombre}">
				</a>
				<div class="desc">${planta.nombre}
					<img src="img/minus.png" alt="Eliminar Planta"
						onClick="javascript:eliminar(${elemento.id},${planta.id})">
				</div>
			</div>
		</c:forEach>
	</div>
	<div>
		<img id="imgPlus" src="img/plus.png" alt="Crear Planta"
			onClick="javascript:irCrear()">
	</div>
	<div id="panelCrearForm" class="gallery" style="display: none">
		<h2>Nueva Planta</h2>
		<form method="post" action="./maceta" id="formPlanta">
			<input hidden="true" type="text" name="accion" value="CREAR" />
			<input hidden="true" type="text" name="id" value="${elemento.id}" />
			<input hidden="true" type="text" name="idPlanta" value="" />
		    <label for="nombre">Identificador:</label> ${proximoId} <br>
		   <label for="nombre">Nombre:</label>
		    <input id="nombre" type="text" name="nombre" value="" required
				pattern="[A-Za-z]+.{3,25}"
				title="El nombre tiene que estar formado por letras y más de 4 caracteress" /><br>
			<label for="username">Propietario Username:</label>
			 <input id="username" type="text"
				name="username" value="${elemento.huerto.usuario.username}" required
				pattern="[A-Za-z]+.{5,8}"
				title="El username tiene que estar formado por letras y de 5 a 8 caracteress" /><br>
				
			<input type="submit" value="Crear"> <input type="button"
				value="Volver" onClick="javascript:visualizar()">
		</form>
	</div>
</body>
</html>