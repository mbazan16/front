package com.huertos.exhuertos.services.interfaces;

import java.util.List;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.entities.Maceta;
import com.huertos.exhuertos.entities.Planta;

public interface IMaceta {
	
	List<Maceta> getfindAll() throws ServiceException;
	
	void modificar(Long id,TipoMaceta tipoMaceta) throws ServiceException;
	
	Maceta getFindById(Long  id) throws ServiceException;
	
	Long getLastIdPlanta() throws ServiceException;
	
	void crearPlanta(Long idMaceta,String nombre,String username) throws ServiceException;
	
	void eliminarPlanta(Long id) throws ServiceException;

	List<Planta> getfindAllByMaceta(Long id) throws ServiceException ;
	
	

}
