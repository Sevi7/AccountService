package com.account.accountservice.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.account.accountservice.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

}