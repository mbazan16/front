package com.huertos.services;

import java.util.ArrayList;
import java.util.List;

import com.huertos.common.exceptions.DAOException;
import com.huertos.common.exceptions.ServiceException;
import com.huertos.dao.HuertoDAO;
import com.huertos.dao.MacetaDAO;
import com.huertos.dao.PlantaDAO;
import com.huertos.dao.UsuarioDAO;
import com.huertos.dao.interfaces.IDAO;
import com.huertos.dao.interfaces.IDAOHuerto;
import com.huertos.dao.interfaces.IDAOUsuario;
import com.huertos.dto.DatosHuertos;
import com.huertos.entities.Huerto;
import com.huertos.entities.Maceta;
import com.huertos.entities.Planta;
import com.huertos.entities.Usuario;
import com.huertos.services.interfaces.IHuerto;

public class ServicesHuerto implements IHuerto {

	IDAOHuerto huertoDAO;
	IDAO<Long, Maceta> macetaDAO;
	IDAO<Long, Planta> plantaDAO;
	IDAOUsuario usuarioDAO;

	public ServicesHuerto() {
		huertoDAO = new HuertoDAO();
		macetaDAO = new MacetaDAO();
		plantaDAO = new PlantaDAO();
		usuarioDAO = new UsuarioDAO();
	}

	

	@Override
	public Huerto getFindById(Long id) throws ServiceException {
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
		List<DatosHuertos> datosHuertos = new ArrayList<DatosHuertos>();
		try {
			List<Huerto> huertos = huertoDAO.findAll();
			for(Huerto huerto:huertos) {
				int numMacetas = getNumMacetas(huerto);
				int numPlantas = getNumPlantas(huerto);
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
	public void crearHuerto(String nombre, String username) throws ServiceException {
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
	public void modicarHuerto(Long id, String nombre) throws ServiceException {
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
	public void eliminarHuerto(Long id) throws ServiceException {
		try {
			
			huertoDAO.delete(id);

		} catch (DAOException daoe) {
			throw new ServiceException(daoe);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	
	private int getNumMacetas(Huerto huerto) throws DAOException {
		 
		return huertoDAO.getNumMacetas(huerto.getId());
	}
	
	private int getNumPlantas(Huerto huerto) throws DAOException {
		 
		return huertoDAO.getNumPlantas(huerto.getId());
	}

}
