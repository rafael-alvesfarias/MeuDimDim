package br.com.mdd.application.service;

import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.Entry;

public interface EntryService {
	
	public abstract void postEntry(Entry e, Account account);

}
