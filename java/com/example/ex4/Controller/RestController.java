package com.example.ex4.Controller;

import com.example.ex4.beans.Logger;
import com.example.ex4.repo.Message;
import com.example.ex4.repo.MessageRepository;
import com.example.ex4.repo.User;
import com.example.ex4.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * A controller to handle json requests from user which called every 10 seconds
 * and refreshing the page.
 * If user is offline it will redirect to the landing page
 */
@Controller
public class RestController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Message repository returns messages table
     * @return message table
     */
    private MessageRepository getMessageRepo() {
        return messageRepository;
    }

    /**
     * Users repository returns users table
     * @return users table
     */
    private UserRepository getUserRepo() {
        return userRepository;
    }

    @Resource(name = "sessionBean")
    private Logger sessionBean;

    /**
     * Get rewuest to handle request for messages
     * @return List of messages
     */
    @GetMapping(value = "/getmessages")
    public @ResponseBody
    List<Message> getMessages() {
        if (!sessionBean.isLoggedIn())
            return null;
        //fetching top 5 messages and reversing to present them
        List<Message> msg = getMessageRepo().findTop5ByOrderByIdDesc();
        Collections.reverse(msg);
        return msg;
    }

    /**
     * Function to handles when requests are called for getting users list
     * @param modelMap
     * @return users json
     */
    @GetMapping(value = "/getusers")
    public @ResponseBody
    List<User> getUsers(ModelAndView modelMap) {

        User temp = getUserRepo().findByUserName(sessionBean.getUserName());
        if (temp != null) {          //adding 10 seconds to the user timer
            temp.setTimeStamp();
            List<User> users = getUserRepo().findAll();
            for (User u : users)
                if (u.timeElapsed())    //deleting every user that hasn't logged in 10 seconds
                    getUserRepo().delete(u);
        }
        return getUserRepo().findAll();
    }
}

