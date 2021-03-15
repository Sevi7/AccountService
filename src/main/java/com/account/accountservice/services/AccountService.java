package com.account.accountservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.account.accountservice.exceptions.AccountServiceException;
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
	
	public void makeTransfer(Account senderAccount, Account receiverAccount, double amount) {
		if(!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
			throw new AccountServiceException("The currency of both accounts are different. The currency of the account " + senderAccount.getId() + " is " + senderAccount.getCurrency() +
					" and the currency of the account " + receiverAccount.getId() + " is " + receiverAccount.getCurrency());
		}

		if(!senderAccount.isTreasury() && senderAccount.getBalance() < amount) {
			throw new AccountServiceException("The Account " + senderAccount.getId() + " is not Treasury and does not have enough balance for the transfer of " + amount + " " + senderAccount.getCurrency());
		}

		senderAccount.debit(amount);
		receiverAccount.credit(amount);
		this.save(senderAccount);
		this.save(receiverAccount);
	}

}
