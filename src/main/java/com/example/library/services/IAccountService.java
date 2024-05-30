package com.example.library.services;

import com.example.library.models.Account;

public interface IAccountService {
    boolean checkAccount(Account account);
    boolean registerAccount(Account account);
}
