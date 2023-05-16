package com.example;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/prueba")
public class MiController extends HttpServlet {
	
	MiServicioImpl miServicio;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		miServicio = new MiServicioImpl();
		
		List<Departamento> departamentos = miServicio.listado();
		
		req.setAttribute("listado", departamentos);
		
		//De la vista se encarga otro controlador
		//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/prueba/v");
		
		//De la vista se encarga el jsp -- el jsp es un controlador
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/departamentos.jsp");
		dispatcher.forward(req, resp);
				
		
				
	}
	
	

}
