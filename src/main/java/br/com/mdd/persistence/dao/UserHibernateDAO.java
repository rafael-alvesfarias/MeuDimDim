package br.com.mdd.persistence.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import br.com.mdd.application.repository.UserRepository;
import br.com.mdd.domain.model.User;

@Repository
public class UserHibernateDAO extends GenericHibernateDAO<User> implements UserRepository {

	
	@Override
	public User findByUsername(String username) {
		String query = "select u from User u where u.username = :username";
		try {
			return getSession()
					.createQuery(query, User.class)
					.setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
