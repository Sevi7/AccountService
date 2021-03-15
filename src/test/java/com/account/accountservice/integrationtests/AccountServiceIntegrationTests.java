package com.account.accountservice.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;


import com.account.accountservice.exceptions.AccountNotFoundException;
import com.account.accountservice.infrastructure.repositories.AccountRepository;
import com.account.accountservice.models.Account;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountServiceIntegrationTests {		
	@Autowired
	private MockMvc mvc;
 
	@Autowired
	private AccountRepository accountRepository;
	
	@BeforeEach
	public void setupEach() {
		this.accountRepository.deleteAll();
	}
	
	@Test
	void testGetAccount() throws Exception {
		//given
		Account account1 = new Account("Adrian", "EUR", 100, false);
		account1 = accountRepository.save(account1);
		
		//when + then
		mvc.perform(get("/accounts/1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json("{'name':'Adrian', 'currency':'EUR', 'balance': 100, 'treasury':false}"));
		
	}
	
	@Test
	void testGetAccountNotExists() throws Exception {
		//given
		
		//when + then
		mvc.perform(get("/accounts/1"))
		.andDo(print())
		.andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof AccountNotFoundException))
	    .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "Account with id 1 was not found"));
	}
	
	@Test
	void testPostAccountEmptyFields() throws Exception {
		//given
		
		//when + then
		mvc.perform(post("/accounts")
			.contentType(MediaType.APPLICATION_JSON)
		    .content("{}"))
		.andDo(print())
		.andExpect(status().is4xxClientError())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("2 errors")))
		.andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Name is required")))
		.andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Currency is required")));
	}
	
	@Disabled
	@Test
	void testPostAccountNegativeBalanceNonTreasury() throws Exception {
		//given
		
		//when + then
		mvc.perform(post("/accounts")
			.contentType(MediaType.APPLICATION_JSON)
		    .content("{'name':'Adrian', 'currency':'EUR', 'balance': -100, 'treasury':false}"))
		.andDo(print())
		.andExpect(status().is4xxClientError())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		.andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Only Treasury Accounts can have a negative balance")));
	}
}