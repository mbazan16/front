package com.huertos.exhuertos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.huertos.exhuertos.common.exceptions.DAOException;
import com.huertos.exhuertos.common.exceptions.MensajesExceptions;
import com.huertos.exhuertos.dao.interfaces.IDAOUsuario;
import com.huertos.exhuertos.entities.Usuario;

/**
 * Gestion del objeto Usuario con la bbdd
 * @author formacion
 *
 */
public class UsuarioDAO implements IDAOUsuario{

	private final static Logger log = Logger.getLogger(UsuarioDAO.class);

	EntityManagerFactory emf;

	EntityManager manager;	


	/**
	 * Método para encontrar un Usuario por su id 
	 * @param id Identificador del Usuario
	 * @return  objeto Usuario
	 * @throws DAOException
	 */
	@Override
	public Usuario findOne(Long id) throws DAOException {
		log.info("Method:[findOne]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Usuario element = null;

		try {
			init();
			element =findBBDD(id);

		}catch (DAOException daoe) {
			throw daoe;
		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}finally {
			destroy();
		}

		return element;
	}

	/**
	 * Método para encontrar tododos los Usuarios de la base de datos
	 * @return Una Lista de Usuarios
	 * @throws DAOException
	 */
	@Override
	public List<Usuario> findAll() throws DAOException {
		log.info("Method:[findAll]");

		List<Usuario> elements = new ArrayList<Usuario>();

		try {
			init();
			elements = manager.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}finally {
			destroy();
		}

		return elements;
	}
	
	
	/**
	 * Método para crear un Usuario de la base de datos
	 * @param element objeto de tipo Usuario
	 * @throws DAOException
	 */
	@Override
	public void create(Usuario element) throws DAOException {
		log.info("Method:[crear]");
		log.debug("Parmetros:[Usuario element:" + element+"]");

		try {
			init();
			validarCrear(element);

			try {
				log.debug("[crear]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.persist(element);
				manager.getTransaction().commit();
				log.debug("[crear]Commit - Creamos usuario");
			} catch (Exception e) {
				log.error("Error", e);
				manager.getTransaction().rollback();
				log.debug("[crear]Rollback");
				throw new DAOException(e);
			}finally {
				destroy();
			}

		} catch (DAOException daoe) {
			throw daoe;
		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}

	}


	/**
	 * Método para modificar un Usuario de la base de datos
	 * @param element objeto de tipo Usuario
	 * @throws DAOException
	 */
	
	@Override
	public void update(Usuario element) throws DAOException {
		log.info("Method:[update]");
		log.debug("Parmetros:[Usuario element:" + element+"]");
		try {
			init();
			Usuario usuarioBBDD = validarModificar(element);
			
			try {
				EntityTransaction et =manager.getTransaction();
				et.begin();
				log.debug("[modificar]Iniciamos transacción");
				usuarioBBDD.setNombre(element.getNombre());				
				manager.getTransaction().commit();
				log.debug("[modificar]Commit - Modificamos usuario");
			} catch (Exception e) {
				log.error("Error", e);
				manager.getTransaction().rollback();				
				log.debug("[modificar]Rollback");
				throw new DAOException(e);
			}finally {
				destroy();
			}

		} catch (DAOException daoe) {
			throw daoe;
		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}


	}


	/**
	 * Método para eliminar un Usuario de la base de datos
	 * @param id Identificador de Usuario
	 * @throws DAOException
	 */
	@Override
	public void delete(Long id) throws DAOException {
		log.info("Method:[eliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		try {
			init();
			Usuario element = validarEliminar(id);

			try {
				log.debug("[eliminar]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.remove(element);
				manager.getTransaction().commit();
				log.debug("[eliminar]Commit - Eliminamos usuario");
			} catch (Exception e) {
				log.error("Error", e);
				manager.getTransaction().rollback();
				log.debug("[eliminar]Rollback");
				throw new DAOException(e);
			}finally {
				destroy();
			}

		} catch (DAOException daoe) {
			throw daoe;
		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}

	}



	@Override
	public Usuario findUsuarioByUsername(String username) throws DAOException {
		log.info("Method:[findUsuarioByUsername]");
		log.debug("Parmetros:[String username:" + username+"]");

		Usuario element = null;
		String query  = "SELECT u FROM Usuario u WHERE u.username=:username";
		
		try {
			init();
			element = manager.createQuery(query,Usuario.class)
								.setParameter("username", username)
								.getSingleResult();
			return element;

		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}finally {
			destroy();
		}
	}

	/**
	 * Método de inicialización de la clase
	 * Se inicializa dos objetos : EntityManagerFactory y EntityManager
	 */
	private void init() {
		log.info("Method:[private init]");

		if(emf==null) emf = Persistence.createEntityManagerFactory("huertos");
		log.debug("[emf]:"+emf);
		if(manager== null||!manager.isOpen()) manager =  emf.createEntityManager();
		log.debug("[manager]:"+manager);
	}
	/**
	 * Método de destruccion del entityManager
	 * Se inicializa dos objetos : EntityManagerFactory y EntityManager
	 */
	private void destroy() {
		manager.close();
	}

	/**
	 * Validaciones para la acción crear Usuario.
	 * Se comprueba que no existe el usuario
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private void validarCrear(Usuario element)  throws DAOException {
		log.info("Method:[private validarCrear]");
		log.debug("Parmetros:[Usuario element:" + element+"]");

		

	}
	/**
	 * Validaciones para la acción modificar Usuario.
	 * Se comprueba que existe el usuario,
	 * que el usuario a modificar no es igualque el existente
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private Usuario validarModificar(Usuario element)  throws DAOException{
		log.info("Method:[private validarModificar]");
		log.debug("Parmetros:[Usuario element:" + element+"]");

		Usuario usuarioBBDD = findBBDD(element.getId());		
		if(usuarioBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_USUARIO);
		return usuarioBBDD;

	}


	/**
	 * Validaciones para la acción modificar Usuario.
	 * Se comprueba que existe el usuario
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	private Usuario validarEliminar(Long id)  throws DAOException{
		log.info("Method:[private validarEliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Usuario usuarioBBDD = findBBDD(id);		
		if(usuarioBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_USUARIO);

		return usuarioBBDD;
	}
	

	/**
	 * Se extrae un Departamente por su id de la BBDD
	 * @param idDireccion
	 * @throws DAOException
	 */
	private Usuario findBBDD(Long id) throws DAOException {
		log.info("Method:[private findBBDD ]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Usuario element = null;

		try {
			element = manager.find(Usuario.class, id);
			log.debug("[private findBBDD ][element]:"+element);

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}

		return element;
	}

}
