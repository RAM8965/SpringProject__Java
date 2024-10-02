package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 /**
 * This is the controller class for handling social media related requests.
 * It contains various endpoints for account registration, login, message creation,
 * retrieval, updating, and deletion.
 */
@RestController
public class SocialMediaController {

     // Injecting the AccountService and MessageService to handle business logic
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;
    
  //Endpoint for registering a new account.
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account newAccount = accountService.register(account);
        if (newAccount != null) {
            return ResponseEntity.ok(newAccount);
        } else {
            return ResponseEntity.status(409).build();// Conflict status if account already exists

        }
    }
    //Endpoint for logging in an existing account.
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loginAccount = accountService.login(account);
        if (loginAccount != null) {
            return ResponseEntity.ok(loginAccount);
        } else {
            return ResponseEntity.status(401).build();// Unauthorized status for invalid credentials
        }
    }

    //Endpoint for creating a new message
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return new ResponseEntity<>(createdMessage, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);// Bad Request status for invalid message
        }
    }
    
    //Endpoint for retrieving all messages.
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    //Endpoint for retrieving a message by its ID.
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.ok().build();
        }
    }
    
    //Endpoint for deleting a message by its ID.
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        int rowsDeleted = messageService.deleteMessage(messageId);
        if (rowsDeleted > 0) {
            return ResponseEntity.ok(rowsDeleted);
        } else {
            return ResponseEntity.ok().build();
        }
    }
    
    // Endpoint for updating a message by its ID.
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        int rowsUpdated = messageService.updateMessage(messageId, message.getMessageText());
        if (rowsUpdated > 0) {
            return ResponseEntity.ok(rowsUpdated);
        } else {
            return ResponseEntity.status(400).build();// Bad Request status if no rows updated
        }
    }
    
    //Endpoint for retrieving all messages by a specific user's account ID.
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        return ResponseEntity.ok(messages);
    }
}