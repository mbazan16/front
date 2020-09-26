package com.huertos.exhuertos.services.interfaces;

import java.util.List;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.entities.Planta;

public interface IPlanta {
	
	List<Planta> getfindAll() throws ServiceException;
	
	void modificar(Long id,String nombre) throws ServiceException;
	
	Planta getFindById(Long  id) throws ServiceException;
	
	

}
