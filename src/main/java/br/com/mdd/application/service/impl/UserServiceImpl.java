package br.com.mdd.application.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.GenericRepository;
import br.com.mdd.application.repository.UserRepository;
import br.com.mdd.application.service.UserService;
import br.com.mdd.domain.model.Role;
import br.com.mdd.domain.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericRepository<Role> roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoles(new HashSet<Role>(roleRepository.findAll(Role.class)));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
