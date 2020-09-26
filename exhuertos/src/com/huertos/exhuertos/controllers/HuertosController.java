package com.huertos.exhuertos.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.services.ServicesHuerto;
import com.huertos.exhuertos.services.interfaces.IHuerto;

import static com.huertos.exhuertos.common.Constantes.*;

@WebServlet("/huertos")
public class HuertosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IHuerto service;

	private static final Logger log = Logger.getLogger(HuertosController.class);

	public HuertosController() {
		super();
		this.service = new ServicesHuerto();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doGet");

		String forward = "/huertos.jsp";
		boolean recarga = false;

		try {
			String accion = request.getParameter("accion");
			String id = request.getParameter("id");

			

			if (accion == null)	accion = ACCION_VER;
			accion = accion.trim();
			log.debug("id:" + id);
			log.debug("accion:" + accion);
			
			if (!comprobarRecarga(request, recarga, accion, id)) {
				
				switch (accion) {

				case ACCION_CREAR:
					crear(request, response);
					break;
				case ACCION_ELIMINAR:
					eliminar(request, response,Long.valueOf(id));
					break;
				}
				log.debug("session-cargo RECARGA:"+request.getSession());				
				request.getSession().setAttribute("RECARGA", accion + id);
				
				visualizar(request);
			}
		} catch (ServiceException e) {
			log.error("ServiceException", e);
			request.setAttribute("error", e.getMensaje());
		}

		log.debug("forward" + forward);
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

	private boolean comprobarRecarga(HttpServletRequest request, boolean recarga, String accion, String id) {
		log.debug("comprobarRecarga");
		Object srecarga = request.getSession().getAttribute(ATRIBUTO_SESSION_RECARGA);
		log.debug("session:"+request.getSession());
		log.debug("srecarga:"+srecarga);
		
		if (!accion.equalsIgnoreCase(ACCION_VER) && srecarga != null && ((String) srecarga).equals(accion+id))
			recarga = true;
		return recarga;
	}
	
	private void visualizar(HttpServletRequest request) throws ServiceException {
		request.setAttribute("titulo", "HUERTOS");
		request.setAttribute("elementos", service.getfindAllDatosHuertos());
		Long proximoId = service.getLastId();
		request.setAttribute("proximoId", proximoId);
	}

	private void crear(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		log.debug("crear");

		String nombre = request.getParameter("nombre");
		String username = request.getParameter("username");

		log.debug("nombre:" + nombre);
		log.debug("username:" + request.getParameter("username"));
		this.service.crear(nombre, username);

	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("eliminar");

		this.service.eliminar(id);

	}

}
