package com.huertos.exhuertos.dao.interfaces;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.entities.Planta;

public interface IDAOPlanta extends IDAO<Long, Planta> {
	
	 public Long getLastId() throws DAOException;
}
