package com.huertos.exhuertos.services.interfaces;

import java.util.List;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.dto.DatosHuertos;
import com.huertos.exhuertos.entities.Huerto;

public interface IHuerto {
	
	List<DatosHuertos> getfindAllDatosHuertos() throws ServiceException;
	
	void crearHuerto(String nombre, String username) throws ServiceException;
	
	void modicarHuerto(Long id,String nombre) throws ServiceException;
	
	void eliminarHuerto(Long id) throws ServiceException;
	
	List<Huerto> getfindAll() throws ServiceException;
	
	Huerto getFindById(Long  id) throws ServiceException;
	
	Long getLastId() throws ServiceException;
	
	Long getLastIdMaceta() throws ServiceException;
	
	

}
