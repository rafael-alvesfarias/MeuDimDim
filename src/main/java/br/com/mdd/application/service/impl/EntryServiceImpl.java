package br.com.mdd.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.EntryRepository;
import br.com.mdd.application.repository.GenericRepository;
import br.com.mdd.application.service.EntryService;
import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.Entry;

@Service
@Transactional
public class EntryServiceImpl implements EntryService {
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericRepository<Account> accountRepository;
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryRepository<Entry> entryRepository;

	@Override
	@Transactional(readOnly = false)
	public void postEntry(Entry entry , Account account) {
		if (account == null) {
			throw new IllegalArgumentException("account is required");
		}
		Account currentAccount = accountRepository.find(account.getId(), Account.class);
		if (currentAccount == null) {
			throw new IllegalArgumentException("invalid account");
		}
		entry.post(currentAccount);
		entryRepository.save(entry);
		accountRepository.save(currentAccount);
	}

}




