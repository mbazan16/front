package com.huertos.exhuertos.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.dao.HuertoDAO;
import com.huertos.exhuertos.dao.MacetaDAO;
import com.huertos.exhuertos.dao.PlantaDAO;
import com.huertos.exhuertos.dao.interfaces.IDAOHuerto;
import com.huertos.exhuertos.dao.interfaces.IDAOMaceta;
import com.huertos.exhuertos.dao.interfaces.IDAOPlanta;
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.entities.Huerto;
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.services.interfaces.IMaceta;

public class ServicesMaceta implements IMaceta{
	private static final Logger log = Logger.getLogger(ServicesMaceta.class);
	
	IDAOHuerto huertoDAO;
	IDAOMaceta macetaDAO;
	IDAOPlanta plantaDAO;

	public ServicesMaceta() {
		huertoDAO = new HuertoDAO();
		macetaDAO = new MacetaDAO();
		plantaDAO = new PlantaDAO();
	}

	@Override
	public Maceta getFindById(Long id) throws ServiceException {
		log.debug("getFindById");
		try {
			
			return macetaDAO.findOne(id);
	
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Maceta> getfindAll() throws ServiceException {
		log.debug("getfindAll");
		try {

			 return macetaDAO.findAll();
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Maceta> getfindAllByHuerto(Long idHuerto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearMaceta(TipoMaceta tipoMaceta, Long idHuerto) throws ServiceException {
		log.debug("crearMaceta");
		try {
			
			Maceta maceta = new Maceta();
			maceta.setId(macetaDAO.getLastId());
			maceta.setTipoMaceta(tipoMaceta);
			
			Huerto huerto= huertoDAO.findOne(idHuerto);			
			maceta.setHuerto(huerto);
			
			macetaDAO.create(maceta);
			
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void modicarMaceta(Long id,TipoMaceta tipoMaceta) throws ServiceException {
		log.debug("modicarMaceta");
		try {
			
			Maceta maceta= macetaDAO.findOne(id);
			maceta.setTipoMaceta(tipoMaceta);
			
			macetaDAO.update(maceta);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void eliminarMaceta(Long id) throws ServiceException {
		log.debug("eliminarMaceta");
		try {
			
			macetaDAO.delete(id);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Long getLastIdPlanta() throws ServiceException {
		log.debug("getLastIdPlanta");
		try {
			
			return plantaDAO.getLastId();

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
