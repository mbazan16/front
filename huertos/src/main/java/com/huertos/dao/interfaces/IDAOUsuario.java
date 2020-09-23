package com.huertos.dao.interfaces;

import com.huertos.common.exceptions.DAOException;
import com.huertos.entities.Usuario;

public interface IDAOUsuario extends IDAO<Long, Usuario> {
	
	public Usuario findUsuarioByUsername(String username) throws DAOException;

}
