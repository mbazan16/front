package com.huertos.exhuertos.services.interfaces;

import java.util.List;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.entities.Maceta;

public interface IMaceta {
	
	List<Maceta> getfindAll() throws ServiceException;
	
	List<Maceta> getfindAllByHuerto(Long idHuerto) throws ServiceException;
	
	void crearMaceta(TipoMaceta tipoMaceta, Long idHuerto) throws ServiceException;
	
	void modicarMaceta(Long id,TipoMaceta tipoMaceta) throws ServiceException;
	
	void eliminarMaceta(Long id) throws ServiceException;
	
	Maceta getFindById(Long  id) throws ServiceException;
	
	Long getLastIdPlanta() throws ServiceException;
	
	

}
