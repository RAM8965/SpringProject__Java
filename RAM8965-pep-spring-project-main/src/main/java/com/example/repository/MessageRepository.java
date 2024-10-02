package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository interface for Message entity.
 * This interface extends JpaRepository, providing CRUD operations and
 * additional custom query methods for the Message entity.
 */


//Custom method to find all messages posted by a specific user.
//This method uses Spring Data JPA's query derivation mechanism to generate the query.
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByPostedBy(Integer postedBy);
}