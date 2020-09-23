package com.huertos.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huertos.common.exceptions.ServiceException;
import com.huertos.entities.Huerto;
import com.huertos.services.ServicesHuerto;
import com.huertos.services.interfaces.IHuerto;

@WebServlet("/huerto")
public class HuertoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IHuerto service;

	private static final Logger log = Logger.getLogger(HuertoController.class);

	public HuertoController() {
		super();
		this.service = new ServicesHuerto();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doGet");

		String forward = "/huerto.jsp";
		
				

		try {
			String accion = request.getParameter("accion");
			String id = request.getParameter("id");
			String elemento = request.getParameter("elemento");
			String idHuerto = request.getParameter("idHuerto");

			log.debug("id:" + id);
			log.debug("accion:" + accion);
			log.debug("elemento:" + elemento);
			log.debug("idHuerto:" + idHuerto);
			
			if(!"HUERTO".equalsIgnoreCase(elemento)) id = idHuerto;

			if (accion == null || !"HUERTO".equalsIgnoreCase(elemento))
				accion = "VER";
			if (accion != null)
				accion = accion.trim();
			log.debug("id final:" + id);
			log.debug("accion final:" + accion);
			switch (accion) {

			case "MODIFICAR":
				modificar(request, response,Long.valueOf(id));
			case "IR_MODIFICAR":
			case "VER":
				visualizar(request, response,Long.valueOf(id));
				break;
			case "CREAR":
				forward = "/";
				crear(request, response);
				break;
			case "ELIMINAR":
				forward = "/";
				eliminar(request, response,Long.valueOf(id));
				break;
			}
		} catch (ServiceException e) {
			log.error("ServiceException", e);
			request.setAttribute("error", e.getMensaje());
		}

		log.debug("forward"+forward);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doPost");
		doGet(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doPut");
		doGet(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doDelete");
		doGet(request, response);
	}

	private void visualizar(HttpServletRequest request, HttpServletResponse response,Long id) throws ServiceException {
		log.debug("visualizar");

		Huerto huerto = this.service.getFindById(id);

		request.setAttribute("titulo", "Huerto");
		request.setAttribute("elemento", huerto);
		request.setAttribute("nombreElementos", "Macetas");

		/*Long proximoId = this.service.findLastId();
		log.debug("proximoId:" + proximoId);
		request.setAttribute("proximoId", ++proximoId);*/
	}

	private void modificar(HttpServletRequest request, HttpServletResponse response,Long id) throws ServiceException {
		log.debug("modificar");

		String nombre = request.getParameter("nombre");		

		this.service.modicarHuerto(id,nombre);

	}

	private void crear(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		log.debug("crear");

		String nombre = request.getParameter("nombre");
		String username = request.getParameter("username");

		log.debug("nombre:" + nombre);
		log.debug("username:" + request.getParameter("username"));
		this.service.crearHuerto(nombre, username);

	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response,Long id) throws ServiceException {
		log.debug("eliminar");

		this.service.eliminarHuerto(id);

	}

}
