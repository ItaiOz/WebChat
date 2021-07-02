package com.example.ex4.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

/**
 * Our Bean to save data in session as long as the user os logged in
 */
@Component
public class Logger implements Serializable {

    private boolean loggedIn = false;    //boolean variable to indicate if user in online or not
    private String userName;             //String variable for user name

    public Logger() {
        setLogoff();
    }

    /**
     * void function to set login variable to true
     */
    public void setLogin() {
        loggedIn = true;
    }

    /**
     * void function to set login variable to false
     */
    public void setLogoff() {
        loggedIn = false;
    }

    /**
     * Bollean function to be called from controller
     * @return value if user is logged in or not
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * function to set the name of the user that has logged in
     * @param userName to be set inside the bean
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Function to return the user name inside the session bean
     * @return string user name
     */
    public String getUserName() {
        return this.userName;
    }
}


