package com.example.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

/**
 * Entity for creating table in the database,
 * Stores user name, boolean value and timestamp
 */
@Entity
public class User{

    //id value
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //user name variable
    @NotEmpty(message = "User name is mandatory")
    private String userName;

    //time stamp for indicating how long the user is online
    private long timeStamp;

    //bbolean value if user os logged in or not
    private boolean loggedIn;

    /**
     * Empty C-tor
     */
    public User() {}

    //C-tor to initialize the class variables
    public User(String userName, long timeStamp) {
        this.userName = userName;
        this.loggedIn = true;
        this.timeStamp = timeStamp;
    }

    /**
     * Function for setting id
     * @param id of the user
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Function for getting id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Function to set user name
     * @param userName string
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Function to set if logged in
     * @param val logged in
     */
    public void setLoggedIn(boolean val) { this.loggedIn = val; }

    /**
     * Function to add 10 secs to user
     */
    public void setTimeStamp() {
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * Function to indicate timme
     * @return time stamp
     */
    public long getTimeStamp() {return this.timeStamp; }

    /**
     * Function to get user name
     * @return user name string
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Bollean Function to return variable
     * @return true or false
     */
    public boolean getLoggedIn() { return loggedIn; }

    /**
     * Function to return the time stamp
     * @return time stamp
     */
    public boolean timeElapsed(){
        return(System.currentTimeMillis() - this.timeStamp > 15000);
    }

    /**
     * String function for getting data
     * @return string
     */
    @Override
    public String toString() { return "User{" + "id=" + id + ", userName=" + userName + '}' ;}
}