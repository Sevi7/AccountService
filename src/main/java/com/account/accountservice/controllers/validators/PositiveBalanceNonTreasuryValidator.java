package com.account.accountservice.controllers.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.account.accountservice.models.Account;

public class PositiveBalanceNonTreasuryValidator implements ConstraintValidator<PositiveBalanceNonTreasuryConstraint, Account> {
	public void initialize(PositiveBalanceNonTreasuryConstraint constraintAnnotation) {
		
    }
   
   @Override
   public boolean isValid(Account account, ConstraintValidatorContext context) {
    	return account.isTreasury()? true : account.getBalance() >= 0;
    }
}
