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
import com.huertos.exhuertos.dao.interfaces.IDAOPlanta;
import com.huertos.exhuertos.entities.Planta;

/**
 * Gestion del objeto Planta con la bbdd
 * @author formacion
 *
 */
public class PlantaDAO implements IDAOPlanta{

	private final static Logger log = Logger.getLogger(PlantaDAO.class);

	EntityManagerFactory emf;

	EntityManager manager;	


	/**
	 * Método para encontrar un Planta por su id 
	 * @param id Identificador del Planta
	 * @return  objeto Planta
	 * @throws DAOException
	 */
	@Override
	public Planta findOne(Long id) throws DAOException {
		log.info("Method:[findOne]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Planta element = null;

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
	 * Método para encontrar tododos los Plantas de la base de datos
	 * @return Una Lista de Plantas
	 * @throws DAOException
	 */
	@Override
	public List<Planta> findAll() throws DAOException {
		log.info("Method:[findAll]");

		List<Planta> elements = new ArrayList<Planta>();

		try {
			init();
			elements = manager.createNamedQuery("Planta.findAll", Planta.class).getResultList();

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}finally {
			destroy();
		}

		return elements;
	}
	
	
	/**
	 * Método para crear un Planta de la base de datos
	 * @param element objeto de tipo Planta
	 * @throws DAOException
	 */
	@Override
	public void create(Planta element) throws DAOException {
		log.info("Method:[crear]");
		log.debug("Parmetros:[Planta element:" + element+"]");

		try {
			init();
			validarCrear(element);

			try {
				log.debug("[crear]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.persist(element);
				manager.getTransaction().commit();
				log.debug("[crear]Commit - Creamos planta");
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
		}finally {
			destroy();
		}

	}


	/**
	 * Método para modificar un Planta de la base de datos
	 * @param element objeto de tipo Planta
	 * @throws DAOException
	 */
	
	@Override
	public void update(Planta element) throws DAOException {
		log.info("Method:[update]");
		log.debug("Parmetros:[Planta element:" + element+"]");
		try {
			init();
			Planta plantaBBDD = validarModificar(element);
			
			try {
				EntityTransaction et =manager.getTransaction();
				et.begin();
				log.debug("[modificar]Iniciamos transacción");
				plantaBBDD.setNombre(element.getNombre());
				manager.getTransaction().commit();
				log.debug("[modificar]Commit - Modificamos planta");
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
		}finally {
			destroy();
		}


	}


	/**
	 * Método para eliminar un Planta de la base de datos
	 * @param id Identificador de Planta
	 * @throws DAOException
	 */
	@Override
	public void delete(Long id) throws DAOException {
		log.info("Method:[eliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		try {
			init();
			Planta element = validarEliminar(id);

			try {
				log.debug("[eliminar]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.remove(element);
				manager.getTransaction().commit();
				log.debug("[eliminar]Commit - Eliminamos planta");
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
	public Long getLastId() throws DAOException {
		String query1  = "SELECT Max(p.id) FROM Planta p";
		Long proximoId = 0L;
		try {
			init();
			proximoId = manager.createQuery(query1,Long.class)
								.getSingleResult();
			if(proximoId==null)proximoId=0L;
			proximoId++;
		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}finally {
			destroy();
		}
		return proximoId;
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
	 * Validaciones para la acción crear Planta.
	 * Se comprueba que no existe el planta
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private void validarCrear(Planta element)  throws DAOException {
		log.info("Method:[private validarCrear]");
		log.debug("Parmetros:[Planta element:" + element+"]");

		

	}
	/**
	 * Validaciones para la acción modificar Planta.
	 * Se comprueba que existe el planta,
	 * que el planta a modificar no es igualque el existente
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private Planta validarModificar(Planta element)  throws DAOException{
		log.info("Method:[private validarModificar]");
		log.debug("Parmetros:[Planta element:" + element+"]");

		Planta plantaBBDD = findBBDD(element.getId());		
		if(plantaBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_PLANTA);
		return plantaBBDD;

	}


	/**
	 * Validaciones para la acción modificar Planta.
	 * Se comprueba que existe el planta
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	private Planta validarEliminar(Long id)  throws DAOException{
		log.info("Method:[private validarEliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Planta plantaBBDD = findBBDD(id);		
		if(plantaBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_PLANTA);

		return plantaBBDD;
	}
	

	/**
	 * Se extrae un Departamente por su id de la BBDD
	 * @param idDireccion
	 * @throws DAOException
	 */
	private Planta findBBDD(Long id) throws DAOException {
		log.info("Method:[private findBBDD ]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Planta element = null;

		try {
			element = manager.find(Planta.class, id);
			log.debug("[private findBBDD ][element]:"+element);

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}

		return element;
	}

}
