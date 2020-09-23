package com.huertos.services.interfaces;

import java.util.List;

import com.huertos.common.exceptions.ServiceException;
import com.huertos.dto.DatosHuertos;
import com.huertos.entities.Huerto;

public interface IHuerto {
	
	List<DatosHuertos> getfindAllDatosHuertos() throws ServiceException;
	
	void crearHuerto(String nombre, String username) throws ServiceException;
	
	void modicarHuerto(Long id,String nombre) throws ServiceException;
	
	void eliminarHuerto(Long id) throws ServiceException;
	
	List<Huerto> getfindAll() throws ServiceException;
	
	Huerto getFindById(Long  id) throws ServiceException;
	
	

}
