package br.com.mdd.application.repository;

import java.util.List;

import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.User;

public interface AccountRepository extends GenericRepository<Account> {
	
	public List<Account> findByUser(User user);

}
