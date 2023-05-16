package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/prueba/v")
public class MiControllerVista extends HttpServlet {
	
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Departamento> departamentos = (List<Departamento>) req.getAttribute("listado");
		
		
		String pagina = "<!DOCTYPE html>\n"
				+ "<html>"
				+ "<head>"
				+ "<title>Mi pagina</title>"
				+ "</head"
				+ "<body>"
				+ "<ul>";
		for(Departamento departamento: departamentos) {
			pagina += "<li>";
			pagina += departamento.getNombre();
			pagina += "</li>";
		}
			   pagina +=  "</ul>"
				+ "</body>"
				+ "</html>";
		
		PrintWriter pw = resp.getWriter();
		pw.print(pagina);
		
	}
	
	

}
