package br.com.mdd.application.repository;

import br.com.mdd.domain.model.User;

public interface UserRepository extends GenericRepository<User> {
	
	User findByUsername(String login);

}
