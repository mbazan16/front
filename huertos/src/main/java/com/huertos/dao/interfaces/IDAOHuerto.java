package com.huertos.dao.interfaces;

import com.huertos.common.exceptions.DAOException;
import com.huertos.entities.Huerto;

public interface IDAOHuerto extends IDAO<Long, Huerto> {
	
	 public int getNumMacetas(Long id) throws DAOException;
	 public int getNumPlantas(Long id) throws DAOException;
	 public Long getLastId() throws DAOException;

}
