package br.com.mdd.application.service;

import br.com.mdd.domain.model.User;

public interface UserService {
	
	public abstract void save(User user);
	
	public abstract User findByUsername(String username);

}
