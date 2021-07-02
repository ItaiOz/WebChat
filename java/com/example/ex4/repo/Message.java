package com.example.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * Messages Entity to store user name and the messages it sent
 */
@Entity
public class Message{

    @GeneratedValue
    @Id
    private long id;

    //String to store the user name
    private String userName;

    //String to be initialized for every message the user enters
    @NotEmpty(message ="Message cannot be blank")
    private String messageContent;

    /**
     * Empty C-tor
     */
    public Message() {}

    /**
     * C-tor to initialize local variable
     * @param userName user name
     * @param message message
     */
    public Message(String userName, String message) {
        this.userName = userName;
        this.messageContent = message;
    }

    /**
     * Function for protocol only, not used in program
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Function to return the id
     * @return Id of message in table
     */
    public long getId() {
        return id;
    }

    /**
     * Function to set the name of the user
     * @param userName as in the class
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Function to return the name of the user
     * @return User name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Function to initialize the message
     * @param message string
     */
    public void setMessage(String message) { this.messageContent = message; }

    /**
     * Function to return the message content
     * @return Message string
     */
    public String getMessage() { return this.messageContent; }

}
