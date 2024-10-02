package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Account entity.
 * This interface extends JpaRepository, providing CRUD operations and 
 * additional custom query methods for the Account entity.
 */

//Custom method to find an Account by its username.
//This method uses Spring Data JPA's query derivation mechanism to generate the query.

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}