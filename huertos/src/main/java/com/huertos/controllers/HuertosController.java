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
import com.huertos.services.ServicesHuerto;
import com.huertos.services.interfaces.IHuerto;

@WebServlet( "/")
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

		String forward = "/index.jsp";

		try {
			request.setAttribute("nombreElementos", "huertos");
			request.setAttribute("elementos", service.getfindAllDatosHuertos());

		} catch (ServiceException e) {
			log.error("DAOException", e);
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

}
