package com.account.accountservice.controllers.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PositiveBalanceNonTreasuryValidator.class)
@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveBalanceNonTreasuryConstraint {
	String message() default "Only Treasury Accounts can have a negative balance";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
