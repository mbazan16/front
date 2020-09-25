package com.huertos.exhuertos.dao.interfaces;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.entities.Huerto;

public interface IDAOHuerto extends IDAO<Long, Huerto> {
	
	 public Long getNumMacetas(Long id) throws DAOException;
	 public Long getNumPlantas(Long id) throws DAOException;
	 public Long getLastId() throws DAOException;
}
