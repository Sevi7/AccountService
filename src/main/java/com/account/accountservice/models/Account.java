package com.account.accountservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Account {
	@Id @GeneratedValue
	private int id;
	private String name;
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
