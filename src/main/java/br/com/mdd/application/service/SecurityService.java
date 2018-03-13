package br.com.mdd.application.service;

public interface SecurityService {
	
	public abstract String findLoggedInUsername();
	
	public abstract void autologin(String username, String password);

}
