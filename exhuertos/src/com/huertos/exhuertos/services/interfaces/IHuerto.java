package com.huertos.exhuertos.services.interfaces;

import java.util.List;

import com.huertos.exhuertos.common.exceptions.ServiceException;
import com.huertos.exhuertos.data.TipoMaceta;
import com.huertos.exhuertos.dto.DatosHuertos;
import com.huertos.exhuertos.entities.Huerto;
import com.huertos.exhuertos.entities.Maceta;

public interface IHuerto {
	
	Long getLastId() throws ServiceException;
	
	Huerto getFindById(Long  id) throws ServiceException;
	
	List<Huerto> getfindAll() throws ServiceException;
	
	List<DatosHuertos> getfindAllDatosHuertos() throws ServiceException;
	
	void crear(String nombre, String username) throws ServiceException;
	
	void modificar(Long id,String nombre) throws ServiceException;
	
	void eliminar(Long id) throws ServiceException;

	
	List<Maceta> getfindAllByHuerto(Long idHuerto) throws ServiceException;
	
	Long getLastIdMaceta() throws ServiceException;
	
	void crearMaceta(Long idHuerto,TipoMaceta tipoMaceta) throws ServiceException;
	
	void eliminarMaceta(Long id) throws ServiceException;
	
	

}
