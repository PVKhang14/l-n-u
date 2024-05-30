package com.example.library.repositories;

import com.example.library.models.Account;
import com.example.library.utils.Repo;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountRepositoryImpl implements IAccountRepository{
    private final Repo repo = Repo.getInstance();
    @Override
    public Optional<Account> getAccountByUsername(String username) {
        String query = String.format("SELECT * FROM users WHERE username = '%s'", username);
        ResultSet rs = repo.executeQuery(query);
        try {
            if(rs.next()) {
                return Optional.of(Account.builder()
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public void save(Account account) {
        String query = String.format("INSERT INTO users (username, password) VALUES ('%s', '%s')", account.getUsername(), account.getPassword());
        String queryUpdate = String.format("UPDATE users SET password = '%s' WHERE username = '%s'", account.getPassword(), account.getUsername());

        ResultSet rs = repo.executeQuery(String.format("SELECT * FROM users WHERE username = '%s'", account.getUsername()));

        try {
            if(rs.next()) {
                repo.executeUpdate(queryUpdate);
            } else {
                repo.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
