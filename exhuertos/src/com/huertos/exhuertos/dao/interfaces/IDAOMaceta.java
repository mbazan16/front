package com.huertos.exhuertos.dao.interfaces;

import java.util.List;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.entities.Maceta;

public interface IDAOMaceta extends IDAO<Long, Maceta> {
	
	 public Long getLastId() throws DAOException;

	public List<Maceta> findAllByIdHuerto(Long idHuerto)throws DAOException;
}
