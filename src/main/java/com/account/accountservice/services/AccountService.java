package com.account.accountservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.account.accountservice.infrastructure.repositories.AccountRepository;
import com.account.accountservice.models.Account;

@Service
public class AccountService {
	private final AccountRepository accountRepository;
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public List<Account> getAll() {
		return accountRepository.findAll();
	}
	
	public Optional<Account> get(int id){
		return accountRepository.findById(id);
	}
	
	public Account save(Account account) {
		return accountRepository.save(account);
	}
	
	public void delete(int id) {
		accountRepository.deleteById(id);
	}

}
