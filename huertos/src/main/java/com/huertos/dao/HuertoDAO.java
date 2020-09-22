package com.huertos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.huertos.common.exceptions.DAOException;
import com.huertos.common.exceptions.MensajesExceptions;
import com.huertos.dao.interfaces.IDAO;
import com.huertos.entities.Huerto;

/**
 * Gestion del objeto Huerto con la bbdd
 * @author formacion
 *
 */
public class HuertoDAO implements IDAO<Long,Huerto>{

	private final static Logger log = Logger.getLogger(HuertoDAO.class);

	EntityManagerFactory emf;

	EntityManager manager;	


	/**
	 * Método para encontrar un Huerto por su id 
	 * @param id Identificador del Huerto
	 * @return  objeto Huerto
	 * @throws DAOException
	 */
	@Override
	public Huerto findOne(Long id) throws DAOException {
		log.info("Method:[findOne]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Huerto element = null;

		try {
			init();
			element =findBBDD(id);

		}catch (DAOException daoe) {
			throw daoe;
		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}

		return element;
	}

	/**
	 * Método para encontrar tododos los Huertos de la base de datos
	 * @return Una Lista de Huertos
	 * @throws DAOException
	 */
	@Override
	public List<Huerto> findAll() throws DAOException {
		log.info("Method:[findAll]");

		List<Huerto> elements = new ArrayList<Huerto>();

		try {
			init();
			elements = manager.createNamedQuery("Huerto.findAll", Huerto.class).getResultList();

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}

		return elements;
	}
	
	
	/**
	 * Método para crear un Huerto de la base de datos
	 * @param element objeto de tipo Huerto
	 * @throws DAOException
	 */
	@Override
	public void create(Huerto element) throws DAOException {
		log.info("Method:[crear]");
		log.debug("Parmetros:[Huerto element:" + element+"]");

		try {
			init();
			validarCrear(element);

			try {
				log.debug("[crear]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.persist(element);
				manager.getTransaction().commit();
				log.debug("[crear]Commit - Creamos huerto");
			} catch (Exception e) {
				log.error("Error", e);
				manager.getTransaction().rollback();
				log.debug("[crear]Rollback");
				throw new DAOException(e);
			}

		} catch (DAOException daoe) {
			throw daoe;
		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}

	}


	/**
	 * Método para modificar un Huerto de la base de datos
	 * @param element objeto de tipo Huerto
	 * @throws DAOException
	 */
	
	@Override
	public void update(Huerto element) throws DAOException {
		log.info("Method:[update]");
		log.debug("Parmetros:[Huerto element:" + element+"]");
		try {
			init();
			Huerto huertoBBDD = validarModificar(element);
			
			try {
				EntityTransaction et =manager.getTransaction();
				et.begin();
				log.debug("[modificar]Iniciamos transacción");
				huertoBBDD.setNombre(element.getNombre());				
				manager.getTransaction().commit();
				log.debug("[modificar]Commit - Modificamos huerto");
			} catch (Exception e) {
				log.error("Error", e);
				manager.getTransaction().rollback();				
				log.debug("[modificar]Rollback");
				throw new DAOException(e);
			}

		} catch (DAOException daoe) {
			throw daoe;
		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}


	}


	/**
	 * Método para eliminar un Huerto de la base de datos
	 * @param id Identificador de Huerto
	 * @throws DAOException
	 */
	@Override
	public void delete(Long id) throws DAOException {
		log.info("Method:[eliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		try {
			init();
			Huerto element = validarEliminar(id);

			try {
				log.debug("[eliminar]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.remove(element);
				manager.getTransaction().commit();
				log.debug("[eliminar]Commit - Eliminamos huerto");
			} catch (Exception e) {
				log.error("Error", e);
				manager.getTransaction().rollback();
				log.debug("[eliminar]Rollback");
				throw new DAOException(e);
			}

		} catch (DAOException daoe) {
			throw daoe;
		}catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
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
		if(manager== null) manager =  emf.createEntityManager();
		log.debug("[manager]:"+manager);
	}

	/**
	 * Validaciones para la acción crear Huerto.
	 * Se comprueba que no existe el huerto
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private void validarCrear(Huerto element)  throws DAOException {
		log.info("Method:[private validarCrear]");
		log.debug("Parmetros:[Huerto element:" + element+"]");

		

	}
	/**
	 * Validaciones para la acción modificar Huerto.
	 * Se comprueba que existe el huerto,
	 * que el huerto a modificar no es igualque el existente
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private Huerto validarModificar(Huerto element)  throws DAOException{
		log.info("Method:[private validarModificar]");
		log.debug("Parmetros:[Huerto element:" + element+"]");

		Huerto huertoBBDD = findBBDD(element.getId());		
		if(huertoBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_HUERTO);
		return huertoBBDD;

	}


	/**
	 * Validaciones para la acción modificar Huerto.
	 * Se comprueba que existe el huerto
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	private Huerto validarEliminar(Long id)  throws DAOException{
		log.info("Method:[private validarEliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Huerto huertoBBDD = findBBDD(id);		
		if(huertoBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_HUERTO);

		return huertoBBDD;
	}
	

	/**
	 * Se extrae un Departamente por su id de la BBDD
	 * @param idDireccion
	 * @throws DAOException
	 */
	private Huerto findBBDD(Long id) throws DAOException {
		log.info("Method:[private findBBDD ]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Huerto element = null;

		try {
			element = manager.find(Huerto.class, id);
			log.debug("[private findBBDD ][element]:"+element);

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}

		return element;
	}

}
