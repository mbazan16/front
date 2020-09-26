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
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.entities.Planta;
import com.huertos.exhuertos.entities.Usuario;
import com.huertos.exhuertos.services.interfaces.IMaceta;

public class ServicesMaceta implements IMaceta{
	private static final Logger log = Logger.getLogger(ServicesMaceta.class);
	
	IDAOHuerto huertoDAO;
	IDAOMaceta macetaDAO;
	IDAOPlanta plantaDAO;
	IDAOUsuario usuarioDAO;

	public ServicesMaceta() {
		huertoDAO = new HuertoDAO();
		macetaDAO = new MacetaDAO();
		plantaDAO = new PlantaDAO();
		usuarioDAO = new UsuarioDAO();
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
	public void modificar(Long id,TipoMaceta tipoMaceta) throws ServiceException {
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

	@Override
	public List<Planta> getfindAllByMaceta(Long id) throws ServiceException  {
		log.debug("getfindAllByMaceta");
		try {
			
			return plantaDAO.findAllByIdMaceta(id);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void crearPlanta(Long idMaceta, String nombre, String username) throws ServiceException {
		log.debug("crearPlanta");
		try {
			
			Planta planta = new Planta();
			planta.setId(plantaDAO.getLastId());
			planta.setNombre(nombre);
			
			Maceta maceta =macetaDAO.findOne(idMaceta);
			planta.setMaceta(maceta);
			
			Usuario usuario =usuarioDAO.findUsuarioByUsername(username);
			planta.setUsuario(usuario);
			
			plantaDAO.create(planta);
			
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void eliminarPlanta(Long id) throws ServiceException {
		log.debug("eliminarPlanta");
		try {
			
			plantaDAO.delete(id);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
