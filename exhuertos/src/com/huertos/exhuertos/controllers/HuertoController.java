package com.huertos.exhuertos.controllers;


import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.entities.Huerto;
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.services.ServicesHuerto;
import com.huertos.exhuertos.services.interfaces.IHuerto;

import static com.huertos.exhuertos.common.Constantes.*;

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
				case ACCION_CREAR:
					crearMaceta(request, response,Long.valueOf(id));					
					break;
				case ACCION_ELIMINAR:
					eliminarMaceta(request, response);
				}
				log.debug("session-codigoRecarga:"+request.getSession());
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

		Huerto huerto = this.service.getFindById(id);
		List<Maceta> macetas = this.service.getfindAllByHuerto(id);
		request.setAttribute("titulo", "Huerto");
		request.setAttribute("elemento", huerto);
		request.setAttribute("macetas", macetas);
		request.setAttribute("nombreElementos", "Macetas");
		request.setAttribute("tiposMacetas", TipoMaceta.getValues());

		
		  Long proximoId = this.service.getLastIdMaceta();
		  log.debug("Maceta-proximoId:" +  proximoId); 
		  request.setAttribute("proximoIdMaceta", proximoId);
		 
	}

	private void modificar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("modificar");

		String nombre = request.getParameter("nombre");

		this.service.modificar(id, nombre);

	}


	private void crearMaceta(HttpServletRequest request, HttpServletResponse response, Long idHuerto) throws ServiceException {
		log.debug("crear");

		String idtipoMaceta = request.getParameter("tipoMaceta");
		TipoMaceta tipoMaceta =null;
		for(TipoMaceta element:TipoMaceta.values()) {
			if(element.getId()==Integer.valueOf(idtipoMaceta))
				tipoMaceta=element;
		}		
		
		log.debug("TipoMaceta:" + tipoMaceta);
		log.debug("idHuerto:" +idHuerto);
		
		this.service.crearMaceta(idHuerto,tipoMaceta);

	}

	private void eliminarMaceta(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		log.debug("eliminar");
		Long idMaceta = Long.valueOf(request.getParameter("idMaceta"));

		this.service.eliminarMaceta(idMaceta);

	}

}
