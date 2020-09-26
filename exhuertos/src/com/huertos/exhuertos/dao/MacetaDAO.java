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
import com.huertos.exhuertos.dao.interfaces.IDAOMaceta;
import com.huertos.exhuertos.entities.Maceta;

/**
 * Gestion del objeto Maceta con la bbdd
 * @author formacion
 *
 */
public class MacetaDAO implements IDAOMaceta{

	private final static Logger log = Logger.getLogger(MacetaDAO.class);

	EntityManagerFactory emf;

	EntityManager manager;	


	/**
	 * Método para encontrar un Maceta por su id 
	 * @param id Identificador del Maceta
	 * @return  objeto Maceta
	 * @throws DAOException
	 */
	@Override
	public Maceta findOne(Long id) throws DAOException {
		log.info("Method:[findOne]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Maceta element = null;

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
	 * Método para encontrar tododos los Macetas de la base de datos
	 * @return Una Lista de Macetas
	 * @throws DAOException
	 */
	@Override
	public List<Maceta> findAll() throws DAOException {
		log.info("Method:[findAll]");

		List<Maceta> elements = new ArrayList<Maceta>();

		try {
			init();
			elements = manager.createNamedQuery("Maceta.findAll", Maceta.class).getResultList();

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}finally {
			destroy();
		}

		return elements;
	}
	
	
	@Override
	public List<Maceta> findAllByIdHuerto(Long idHuerto) throws DAOException{
		String query1  = "SELECT m FROM Maceta m where m.huerto.id=:idHuerto";
		List<Maceta> macetas = new ArrayList<Maceta>();
		try {
			init();
			macetas = manager.createQuery(query1,Maceta.class)
							.setParameter("idHuerto", idHuerto)
							.getResultList();
		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);
		}finally {
			destroy();
		}
		return macetas;
	}

	/**
	 * Método para crear un Maceta de la base de datos
	 * @param element objeto de tipo Maceta
	 * @throws DAOException
	 */
	@Override
	public void create(Maceta element) throws DAOException {
		log.info("Method:[crear]");
		log.debug("Parmetros:[Maceta element:" + element+"]");
		log.debug("Parmetros:[TipoMaceta element:" + element.getTipoMaceta()+"]");

		try {
			init();
			validarCrear(element);

			try {
				log.debug("[crear]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.persist(element);
				manager.getTransaction().commit();
				log.debug("[crear]Commit - Creamos maceta");
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
	 * Método para modificar un Maceta de la base de datos
	 * @param element objeto de tipo Maceta
	 * @throws DAOException
	 */
	
	@Override
	public void update(Maceta element) throws DAOException {
		log.info("Method:[update]");
		log.debug("Parmetros:[Maceta element:" + element+"]");
		try {
			init();
			Maceta macetaBBDD = validarModificar(element);
			
			try {
				EntityTransaction et =manager.getTransaction();
				et.begin();
				log.debug("[modificar]Iniciamos transacción");
				macetaBBDD.setTipoMaceta(element.getTipoMaceta());
				macetaBBDD.setCompleta(element.isCompleta());
				manager.getTransaction().commit();
				log.debug("[modificar]Commit - Modificamos maceta");
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
	 * Método para eliminar un Maceta de la base de datos
	 * @param id Identificador de Maceta
	 * @throws DAOException
	 */
	@Override
	public void delete(Long id) throws DAOException {
		log.info("Method:[eliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		try {
			init();
			Maceta element = validarEliminar(id);

			try {
				log.debug("[eliminar]Iniciamos transacción");
				manager.getTransaction().begin();
				manager.remove(element);
				manager.getTransaction().commit();
				log.debug("[eliminar]Commit - Eliminamos maceta");
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
		}finally {
			destroy();
		}

	}



	@Override
	public Long getLastId() throws DAOException {
		String query1  = "SELECT Max(m.id) FROM Maceta m";
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
	 * Validaciones para la acción crear Maceta.
	 * Se comprueba que no existe el maceta
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private void validarCrear(Maceta element)  throws DAOException {
		log.info("Method:[private validarCrear]");
		log.debug("Parmetros:[Maceta element:" + element+"]");

		

	}
	/**
	 * Validaciones para la acción modificar Maceta.
	 * Se comprueba que existe el maceta,
	 * que el maceta a modificar no es igualque el existente
	 * y que la direccion esta registrada en la BBDD
	 * Si exite el manager, también se comprueba su existencia
	 * 
	 * @param element
	 * @throws DAOException
	 */
	private Maceta validarModificar(Maceta element)  throws DAOException{
		log.info("Method:[private validarModificar]");
		log.debug("Parmetros:[Maceta element:" + element+"]");

		Maceta macetaBBDD = findBBDD(element.getId());		
		if(macetaBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_MACETA);
		return macetaBBDD;

	}


	/**
	 * Validaciones para la acción modificar Maceta.
	 * Se comprueba que existe el maceta
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	private Maceta validarEliminar(Long id)  throws DAOException{
		log.info("Method:[private validarEliminar]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Maceta macetaBBDD = findBBDD(id);		
		if(macetaBBDD == null) throw new DAOException(MensajesExceptions.NO_EXISTE_MACETA);

		return macetaBBDD;
	}
	

	/**
	 * Se extrae un Departamente por su id de la BBDD
	 * @param idDireccion
	 * @throws DAOException
	 */
	private Maceta findBBDD(Long id) throws DAOException {
		log.info("Method:[private findBBDD ]");
		log.debug("Parmetros:[Long id:" + id+"]");

		Maceta element = null;

		try {
			element = manager.find(Maceta.class, id);
			log.debug("[private findBBDD ][element]:"+element);

		} catch (Exception e) {
			log.error("Error", e);
			throw new DAOException(e);

		}

		return element;
	}

}
