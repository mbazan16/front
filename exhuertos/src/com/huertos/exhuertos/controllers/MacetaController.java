package com.huertos.exhuertos.controllers;

import static com.huertos.exhuertos.common.Constantes.ACCION_CREAR;
import static com.huertos.exhuertos.common.Constantes.ACCION_ELIMINAR;
import static com.huertos.exhuertos.common.Constantes.ACCION_MODIFICAR;
import static com.huertos.exhuertos.common.Constantes.ACCION_VER;
import static com.huertos.exhuertos.common.Constantes.ATRIBUTO_SESSION_RECARGA;

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
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.entities.Planta;
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
					crearPlanta(request, response, Long.valueOf(id));
					break;
				case ACCION_ELIMINAR:
					eliminarPlanta(request, response);
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

		Maceta maceta = this.service.getFindById(id);
		List<Planta> plantas = this.service.getfindAllByMaceta(id);
		request.setAttribute("titulo", "Maceta");
		request.setAttribute("elemento", maceta);
		request.setAttribute("plantas", plantas);
		request.setAttribute("nombreElementos", "Plantas");
		request.setAttribute("tiposMacetas", TipoMaceta.getValues());

		
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

		this.service.modificar(id, tipoMaceta);

	}
	private void crearPlanta(HttpServletRequest request, HttpServletResponse response, Long idMaceta) throws ServiceException {
		log.debug("crear");

		String nombre = request.getParameter("nombre");
		String username = request.getParameter("username");

		log.debug("nombre:" + nombre);
		log.debug("username:" + request.getParameter("username"));
		
		this.service.crearPlanta(idMaceta,nombre,username);

	}

	private void eliminarPlanta(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		log.debug("eliminar");
		Long idPlanta = Long.valueOf(request.getParameter("idPlanta"));

		this.service.eliminarPlanta(idPlanta);

	}

}
