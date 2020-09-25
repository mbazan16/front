package com.huertos.exhuertos.dao.interfaces;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.entities.Usuario;

public interface IDAOUsuario extends IDAO<Long, Usuario> {
	
	public Usuario findUsuarioByUsername(String username) throws DAOException;

}
