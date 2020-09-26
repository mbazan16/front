package com.huertos.exhuertos.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.dao.HuertoDAO;
import com.huertos.exhuertos.dao.MacetaDAO;
import com.huertos.exhuertos.dao.UsuarioDAO;
import com.huertos.exhuertos.dao.interfaces.IDAOHuerto;
import com.huertos.exhuertos.dao.interfaces.IDAOMaceta;
import com.huertos.exhuertos.dao.interfaces.IDAOUsuario;
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.dto.DatosHuertos;
import com.huertos.exhuertos.entities.Huerto;
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.entities.Usuario;
import com.huertos.exhuertos.services.interfaces.IHuerto;

public class ServicesHuerto implements IHuerto {
	private static final Logger log = Logger.getLogger(ServicesHuerto.class);
	
	IDAOHuerto huertoDAO;
	IDAOMaceta macetaDAO;
	IDAOUsuario usuarioDAO;

	public ServicesHuerto() {
		huertoDAO = new HuertoDAO();
		macetaDAO = new MacetaDAO();
		usuarioDAO = new UsuarioDAO();
	}

	

	@Override
	public Huerto getFindById(Long id) throws ServiceException {
		log.debug("getFindById");
		try {
			
			return huertoDAO.findOne(id);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Huerto> getfindAll() throws ServiceException {
		log.debug("getfindAll");
		try {

			 return huertoDAO.findAll();
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public List<DatosHuertos> getfindAllDatosHuertos() throws ServiceException {
		log.debug("getfindAllDatosHuertos");
		List<DatosHuertos> datosHuertos = new ArrayList<DatosHuertos>();
		try {
			List<Huerto> huertos = huertoDAO.findAll();
			for(Huerto huerto:huertos) {
				Long numMacetas = getNumMacetas(huerto);
				Long numPlantas = getNumPlantas(huerto);
				Long id= huerto.getId();
				String nombre = huerto.getNombre();
				Usuario usuario = huerto.getUsuario();
				DatosHuertos datosHuerto= new DatosHuertos(id,nombre,usuario,numMacetas,numPlantas);
				datosHuertos.add(datosHuerto);
			}
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return datosHuertos;
	}

	

	@Override
	public Long getLastId() throws ServiceException {
		log.debug("getLastId");
		try {
			
			return huertoDAO.getLastId();

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}



	@Override
	public Long getLastIdMaceta() throws ServiceException {
		log.debug("getLastIdMaceta");
		try {
			
			return macetaDAO.getLastId();

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}



	@Override
	public void crear(String nombre, String username) throws ServiceException {
		log.debug("crearHuerto");
		try {
			
			Huerto huerto = new Huerto();
			huerto.setId(huertoDAO.getLastId());
			huerto.setNombre(nombre);
			
			Usuario usuario =usuarioDAO.findUsuarioByUsername(username);
			huerto.setUsuario(usuario);
			
			huertoDAO.create(huerto);
			
		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void modificar(Long id, String nombre) throws ServiceException {
		log.debug("modicarHuerto");
		try {
			
			Huerto huerto= huertoDAO.findOne(id);
			huerto.setNombre(nombre);
			
			huertoDAO.update(huerto);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void eliminar(Long id) throws ServiceException {
		log.debug("eliminarHuerto");
		try {
			
			huertoDAO.delete(id);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}
	
	@Override
	public List<Maceta> getfindAllByHuerto(Long idHuerto) throws ServiceException {
		log.debug("getfindAllByHuerto");
		try {
			
			return macetaDAO.findAllByIdHuerto(idHuerto);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void crearMaceta(Long idHuerto,TipoMaceta tipoMaceta) throws ServiceException {
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

	
	private Long getNumMacetas(Huerto huerto) throws DAOException {
		 
		return huertoDAO.getNumMacetas(huerto.getId());
	}
	
	private Long getNumPlantas(Huerto huerto) throws DAOException {
		 
		return huertoDAO.getNumPlantas(huerto.getId());
	}

}
