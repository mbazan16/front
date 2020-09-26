package com.huertos.exhuertos.controllers;

import static com.huertos.exhuertos.common.Constantes.ACCION_MODIFICAR;
import static com.huertos.exhuertos.common.Constantes.ACCION_VER;
import static com.huertos.exhuertos.common.Constantes.ATRIBUTO_SESSION_RECARGA;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.entities.Planta;
import com.huertos.exhuertos.services.ServicesPlanta;
import com.huertos.exhuertos.services.interfaces.IPlanta;

@WebServlet("/planta")
public class PlantaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IPlanta service;

	private static final Logger log = Logger.getLogger(PlantaController.class);

	public PlantaController() {
		super();
		this.service = new ServicesPlanta();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doGet");

		String forward = "/planta.jsp";
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

				case ACCION_MODIFICAR:
					modificar(request, response, Long.valueOf(id));
					break;
				}
				log.debug("session-METO RECARGA:"+request.getSession());
				request.getSession().setAttribute("RECARGA", accion + id);
				
				visualizar(request, response, Long.valueOf(id));
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

	private void visualizar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("visualizar");

		Planta planta = this.service.getFindById(id);

		request.setAttribute("titulo", "Planta");
		request.setAttribute("elemento", planta);

		 
	}

	private void modificar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("modificar");

		String nombre = request.getParameter("nombre");
		

		this.service.modificar(id, nombre);

	}

}
