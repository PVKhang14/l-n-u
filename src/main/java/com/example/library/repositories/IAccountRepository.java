package com.example.library.repositories;

import com.example.library.models.Account;

import java.util.Optional;

public interface IAccountRepository {
    Optional<Account> getAccountByUsername(String username);
    void save(Account account);
}
