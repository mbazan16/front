package com.huertos.exhuertos.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.dao.HuertoDAO;
import com.huertos.exhuertos.dao.MacetaDAO;
import com.huertos.exhuertos.dao.PlantaDAO;
import com.huertos.exhuertos.dao.UsuarioDAO;
import com.huertos.exhuertos.dao.interfaces.IDAOHuerto;
import com.huertos.exhuertos.dao.interfaces.IDAOMaceta;
import com.huertos.exhuertos.dao.interfaces.IDAOPlanta;
import com.huertos.exhuertos.dao.interfaces.IDAOUsuario;
import com.huertos.exhuertos.entities.Planta;
import com.huertos.exhuertos.services.interfaces.IPlanta;

public class ServicesPlanta implements IPlanta {
	private static final Logger log = Logger.getLogger(ServicesPlanta.class);
	
	IDAOHuerto huertoDAO;
	IDAOMaceta macetaDAO;
	IDAOPlanta plantaDAO;
	IDAOUsuario usuarioDAO;

	public ServicesPlanta() {
		huertoDAO = new HuertoDAO();
		macetaDAO = new MacetaDAO();
		plantaDAO = new PlantaDAO();
		usuarioDAO = new UsuarioDAO();
	}

	@Override
	public Planta getFindById(Long id) throws ServiceException {
		log.debug("getFindById");
		try {
			
			return plantaDAO.findOne(id);
	
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Planta> getfindAll() throws ServiceException {
		log.debug("getfindAll");
		try {

			 return plantaDAO.findAll();
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void modificar(Long id,String nombre) throws ServiceException {
		log.debug("modificar");
		try {
			
			Planta planta= plantaDAO.findOne(id);
			planta.setNombre(nombre);;
			
			plantaDAO.update(planta);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	

}
