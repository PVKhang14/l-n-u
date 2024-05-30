package com.example.library.services;

import com.example.library.models.Account;
import com.example.library.repositories.AccountRepositoryImpl;
import com.example.library.repositories.IAccountRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


public class AccountServiceImpl implements IAccountService{

    private final IAccountRepository accountRepository;

    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
    }
    @Override
    public boolean checkAccount(Account account) {
        if (accountRepository.getAccountByUsername(account.getUsername()).isPresent()) {
            return accountRepository.getAccountByUsername(account.getUsername()).get().getPassword().equals(account.getPassword());
        }
        return false;
    }

    @Override
    public boolean registerAccount(Account account) {
        if (accountRepository.getAccountByUsername(account.getUsername()).isPresent()) {
            return false;
        }

        accountRepository.save(account);
        return true;
    }
}
