package com.son.SpringFilter.repository;

import com.son.SpringFilter.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUsername(String username);

}
