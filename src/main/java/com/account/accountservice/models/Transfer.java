package com.account.accountservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
	@NotNull
	private Integer senderAccount;
	
	@NotNull
	private Integer receiverAccount;
	
	@NotNull
	@Positive
	private Double amount;
}
