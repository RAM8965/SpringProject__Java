package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing message-related operations.
 * This class provides methods for posting, retrieving, updating, and deleting messages.
 */

@Service
public class MessageService {

    // Injecting the MessageRepository to interact with the database for message-related operations
    @Autowired
    private MessageRepository messageRepository;

    // Injecting the AccountRepository to interact with the database for account-related operations
    @Autowired
    private AccountRepository accountRepository;

    //Posts a new message.
    public Message postMessage(Message message) {

        // Check if the message text is blank or exceeds 255 characters
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return null;// Return null if validation fails
        }
        return messageRepository.save(message);// Save and return the new message
    }

    //Retrieves all messages.
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Retrieves a message by its ID.
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    // Deletes a message by its ID.
    public int deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;// Return 1 if deletion is successful
        }
        return 0;// Return 0 if the message does not exist
    }

    //Updates the text of a message by its ID.
    public int updateMessage(Integer messageId, String messageText) {

        // Check if the new message text is blank or exceeds 255 characters
        if (messageText.isBlank() || messageText.length() > 255) {
            return 0;// Return 0 if validation fails
        }
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;// Return 1 if updating is successful
        }
        return 0;// Return 0 if the message does not exist
    }

    //Retrieves all messages posted by a specific user.
    public List<Message> getMessagesByUserId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    //Creates a new message with additional validation.
    public Message createMessage(Message message) throws IllegalArgumentException {

        // Check if the message text is null or empty
        if (message.getMessageText() == null || message.getMessageText().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be blank");
        }

        // Check if the message text exceeds 255 characters
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text must be under 255 characters");
        }

        // Check if the user posting the message exists
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("User not found");
        }

         // Save and return the new message
        return messageRepository.save(message);
    }
    
}