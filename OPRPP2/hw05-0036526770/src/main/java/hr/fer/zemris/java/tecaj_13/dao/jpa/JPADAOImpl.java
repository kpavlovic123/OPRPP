package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public <T> T getEntity(Long id,Class<T> entityClass) throws DAOException{
		return JPAEMProvider.getEntityManager().find(entityClass, id);
	}

	@Override
	public <T> void persistEntity(T entity){
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entity);
	}
}