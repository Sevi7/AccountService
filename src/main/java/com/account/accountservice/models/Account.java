package com.account.accountservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.account.accountservice.controllers.validators.PositiveBalanceNonTreasuryConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@PositiveBalanceNonTreasuryConstraint
public class Account {
	@Id @GeneratedValue
	private int id;
	@NotEmpty(message = "Name is required")
	private String name;
	@NotEmpty(message = "Currency is required")
	private String currency;
	@Setter
	private double balance;
	private boolean treasury;

	public Account (String name, String currency, double balance, boolean treasury) {
		this.name=name;
		this.currency=currency;
		this.balance=balance;
		this.treasury=treasury;
	}

}
