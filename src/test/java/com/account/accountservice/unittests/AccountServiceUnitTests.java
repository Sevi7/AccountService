package com.account.accountservice.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.accountservice.infrastructure.repositories.AccountRepository;
import com.account.accountservice.models.Account;
import com.account.accountservice.services.AccountService;
import com.account.accountservice.exceptions.AccountServiceException;

@ExtendWith(MockitoExtension.class)
public class AccountServiceUnitTests {
	@Mock
	private AccountRepository accountRepository;

	private AccountService accountService;
	
	@BeforeEach
	void initService() {
		this.accountService = new AccountService(accountRepository);
	}
	
	@Test
	void makeTransferAccountsDifferentCurrenciesThrowsError() {
		Account senderAccount = new Account("Adrian", "EUR", 100, false);
		Account receiverAccount = new Account("Fran", "GBP", 100, false);
		Exception exception = assertThrows(AccountServiceException.class, () -> {
			this.accountService.makeTransfer(senderAccount, receiverAccount, 50);
		});
		
		String expectedMessage = "The currency of both accounts are different. The currency of the account 0 is EUR and the currency of the account 0 is GBP";
		assertEquals(exception.getMessage(), expectedMessage);
	}
	
	@Test
	void makeTransferNonTreasuryAccountNotEnoughBalanceThrowsError() {
		Account senderAccount = new Account("Adrian", "EUR", 5, false);
		Account receiverAccount = new Account("Fran", "EUR", 100, false);
		Exception exception = assertThrows(AccountServiceException.class, () -> {
			this.accountService.makeTransfer(senderAccount, receiverAccount, 50);
		});
		
		String expectedMessage = "The Account 0 is not Treasury and does not have enough balance for the transfer of 50.0 EUR";
		assertEquals(exception.getMessage(), expectedMessage);
	}
	
	@Test
	void makeTransferTreasuryAccountNotEnoughBalance() {
		Account senderAccount = new Account("Adrian", "EUR", 5, true);
		Account receiverAccount = new Account("Fran", "EUR", 100, false);
		
		when(accountRepository.save(any(Account.class))).thenReturn(null);
		
		this.accountService.makeTransfer(senderAccount, receiverAccount, 50);
		
		assertEquals(senderAccount.getBalance(), -45);
		assertEquals(receiverAccount.getBalance(), 150);
	}
	
	@Test
	void makeTransferNonTreasuryAccountEnoughBalance() {
		Account senderAccount = new Account("Adrian", "EUR", 50, false);
		Account receiverAccount = new Account("Fran", "EUR", 100, false);
		
		when(accountRepository.save(any(Account.class))).thenReturn(null);
		
		this.accountService.makeTransfer(senderAccount, receiverAccount, 50);
		
		assertEquals(senderAccount.getBalance(), 0);
		assertEquals(receiverAccount.getBalance(), 150);
	}
	
	@Test
	void makeTransferTreasuryAccountEnoughBalance() {
		Account senderAccount = new Account("Adrian", "EUR", 50, true);
		Account receiverAccount = new Account("Fran", "EUR", 100, false);
		
		when(accountRepository.save(any(Account.class))).thenReturn(null);
		
		this.accountService.makeTransfer(senderAccount, receiverAccount, 50);
		
		assertEquals(senderAccount.getBalance(), 0);
		assertEquals(receiverAccount.getBalance(), 150);
	}
	
}
