package com.account.accountservice.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.account.accountservice.services.AccountService;
import com.account.accountservice.exceptions.AccountNotFoundException;
import com.account.accountservice.models.Account;
import com.account.accountservice.models.Transfer;

@RestController
public class AccountController {
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping(value="/accounts")
	public List<Account> getAll() {
		return this.accountService.getAll();
	}
	
	@GetMapping(value="/accounts/{id}")
	public Account get(@PathVariable @Min(1) int id) {
		return accountService.get(id)
							.orElseThrow(()-> new AccountNotFoundException("Account with id " + id + " was not found"));
	}
	
	@PostMapping(value="/accounts")
	public Account create(@Valid @RequestBody Account account) {
		return accountService.save(account);
	}
	
	@PostMapping(value="/accounts/transfer")
	public Transfer create(@Valid @RequestBody Transfer transfer) {
		Account senderAccount = accountService.get(transfer.getSenderAccount())
				.orElseThrow(()-> new AccountNotFoundException("Account with id " + transfer.getSenderAccount() + " was not found"));
		Account receiverAccount = accountService.get(transfer.getReceiverAccount())
				.orElseThrow(()-> new AccountNotFoundException("Account with id " + transfer.getReceiverAccount() + " was not found"));
		
		this.accountService.makeTransfer(senderAccount, receiverAccount, transfer.getAmount());
		
		return transfer;
	}
	
	
}
