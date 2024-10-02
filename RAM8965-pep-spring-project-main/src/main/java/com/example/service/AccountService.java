package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing account-related operations.
 * This class provides methods for registering and logging in accounts.
 */

@Service
public class AccountService {

    // Injecting the AccountRepository to interact with the database
    @Autowired
    private AccountRepository accountRepository;

    //Registers a new account.
    public Account register(Account account) {

        // Check if the username is blank or the password length is less than 4 characters
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;// Return null if validation fails
        }

        // Check if an account with the same username already exists
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;// Return null if username is already taken
        }
        return accountRepository.save(account);// Save and return the new account
    }

    // Logs in an existing account.
    public Account login(Account account) {

        // Retrieve the account from the repository by username
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

         // Check if the account exists and the password matches
        if (existingAccount.isPresent() && existingAccount.get().getPassword().equals(account.getPassword())) {
            return existingAccount.get();// Return the account if login is successful
        }
        return null;// Return null if login fails
    }
    
}