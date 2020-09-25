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
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.services.ServicesMaceta;
import com.huertos.exhuertos.services.interfaces.IMaceta;

@WebServlet("/maceta")
public class MacetaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IMaceta service;

	private static final Logger log = Logger.getLogger(MacetaController.class);

	public MacetaController() {
		super();
		this.service = new ServicesMaceta();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doGet");

		String forward = "/maceta.jsp";
		boolean recarga = false;

		try {
			String accion = request.getParameter("accion");
			String id = request.getParameter("id");

			log.debug("id:" + id);
			log.debug("accion:" + accion);

			if (accion == null)
				accion = "VER";
			if (accion != null)
				accion = accion.trim();
			log.debug("id final:" + id);
			log.debug("accion final:" + accion);
			recarga = comprobarRecarga(request, recarga, accion, id);

			if (!recarga) {
				log.debug("srecarga:"+request.getSession().getAttribute("RECARGA"));
				switch (accion) {

				case "MODIFICAR":
					modificar(request, response, Long.valueOf(id));
				case "IR_MODIFICAR":
				case "VER":
					visualizar(request, response, Long.valueOf(id));
					break;
				case "CREAR":
					forward = "/huerto";
					crear(request, response);
					break;
				case "ELIMINAR":
					forward = "/huerto";
					eliminar(request, response, Long.valueOf(id));
					break;
				}
				log.debug("session-METO RECARGA:"+request.getSession());
				request.getSession().setAttribute("RECARGA", accion + id);
			} else {
				forward = "/huertos";
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
		Object srecarga = request.getSession().getAttribute("RECARGA");
		log.debug("session:"+request.getSession());
		log.debug("srecarga:"+srecarga);
		
		if (srecarga != null && ((String) srecarga).equals(accion+id))
			recarga = true;
		return recarga;
	}

	private void visualizar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("visualizar");

		Maceta maceta = this.service.getFindById(id);

		request.setAttribute("titulo", "Maceta");
		request.setAttribute("elemento", maceta);
		request.setAttribute("nombreElementos", "Plantas");

		
		  Long proximoId = this.service.getLastIdPlanta();
		  log.debug("Planta-proximoId:" +  proximoId); 
		  request.setAttribute("proximoIdPlanta", proximoId);
		 
	}

	private void modificar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("modificar");

		String idtipoMaceta = request.getParameter("tipoMaceta");
		TipoMaceta tipoMaceta =null;
		for(TipoMaceta element:TipoMaceta.values()) {
			if(element.getId()==Integer.valueOf(idtipoMaceta))
				tipoMaceta=element;
		}

		this.service.modicarMaceta(id, tipoMaceta);

	}

	private void crear(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		log.debug("crear");

		String idtipoMaceta = request.getParameter("tipoMaceta");
		TipoMaceta tipoMaceta =null;
		for(TipoMaceta element:TipoMaceta.values()) {
			if(element.getId()==Integer.valueOf(idtipoMaceta))
				tipoMaceta=element;
		}
		
		Long idHuerto = Long.valueOf(request.getParameter("idHuerto"));

		log.debug("TipoMaceta:" + tipoMaceta);
		log.debug("idHuerto:" + request.getParameter("idHuerto"));
		this.service.crearMaceta(tipoMaceta, idHuerto);

	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response, Long id) throws ServiceException {
		log.debug("eliminar");

		this.service.eliminarMaceta(id);

	}

}
