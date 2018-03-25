package br.com.mdd.persistence.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.mdd.application.repository.AccountRepository;
import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.User;

@Repository("accountDAO")
public class AccountHibernateDAO extends GenericHibernateDAO<Account> implements AccountRepository {
	
	@Override
	public List<Account> findByUser(User user) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Account> query = builder.createQuery(Account.class);
		Root<Account> e = query.from(Account.class);
		ParameterExpression<User> userParam = builder.parameter(User.class, "user");
		query.where(builder.equal(e.get("user"), userParam));

		return getSession().createQuery(query).setParameter("user", user).getResultList();
	}
}