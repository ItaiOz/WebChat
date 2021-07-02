package com.example.ex4;

import com.example.ex4.beans.Logger;
import com.example.ex4.repo.Message;
import com.example.ex4.repo.MessageRepository;
import com.example.ex4.repo.User;
import com.example.ex4.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;

/**
 * Main controller to handle post and get request in chat pages
 */
@Controller
public class WebController {

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository userRepository;                  //repository for users table

    @Autowired
    private MessageRepository messageRepository;            //repository for messages table

    /**
     * Returning repository
     * @return  userRepository
     */
    private UserRepository getUserRepo() { return userRepository;   }
    /**
     * Returning repository
     * @return  messagesRepository
     */
    private MessageRepository getMessageRepo() {  return messageRepository; }


    @Resource(name="sessionBean")
    protected Logger sessionBean;


    /**
     * Landing page request, home page
     * @param model
     * @return login html, or vhat page if logged in
     */
    @GetMapping("/")
    public String main(Model model){

        if(sessionBean.isLoggedIn()) {      //if we are still in the session (about 60 minutes)
            checkUserOnline();
            model.addAttribute("name", sessionBean.getUserName());
            return "chat";
        }
        else
            return "login";
    }

    /**
     * Landing page request, home page
     * @return login html, or vhat page if logged in
     */
    @PostMapping("/")
    public String turnSessionOff(){

        if (!sessionBean.isLoggedIn())
            return "login";

        sessionBean.setLogoff();
        User temp = getUserRepo().findUserByUserName(sessionBean.getUserName());
        sessionBean.setUserName("");
        getUserRepo().delete(temp);
        return "login";
    }

    /**
     * Chat page to communicate on page and text messages
     * @param model
     * @return chat page
     */
    @GetMapping("/chatroom")
    public String process(Model model){

        if(!sessionBean.isLoggedIn())
            return "../static/html/notLogged";

         checkUserOnline();             //checking if users are online to load it

         model.addAttribute("name", sessionBean.getUserName());
         model.addAttribute("messages", getMessageRepo().findAll());
         model.addAttribute("users", getUserRepo().findAll());
         return "chat";

    }

    /**
     * Chat page to communicate on page and text messages
     * @param model
     * @return chat page
     */
    @PostMapping("/chatroom")
    public String foo(@RequestParam(name = "name")String name, Model model){

        if (sessionBean.isLoggedIn())
            return process( model);

        name = name.trim();
        User temp = getUserRepo().findUserByUserName(name);

        if(temp != null){
            model.addAttribute("error", "User name is already taken");
            return "login";
        }

        if(name.length() == 0 || name.length() > 20)
        {
            model.addAttribute("error", "User name is mandatory");
            return "login";
        }

        User user = new User(name, System.currentTimeMillis());
        getUserRepo().save(user);

        sessionBean.setLogin();         //setting the session to be true - user has logged in successfully
        sessionBean.setUserName(name);  //setting the name in the session

        model.addAttribute("name", name);
        model.addAttribute("users", getUserRepo().findAll());
//        return new ModelAndView("redirect:/chatroom");
        return "chat";
    }

    /**
     * adding messages from user
     * @param model
     * @return chat page
     */
    @GetMapping("/add-message")
    public ModelAndView redirectToChat(ModelMap model){
        return new ModelAndView("redirect:/chatroom", model);
    }

    /**
     * adding messages from user
     * @param model
     * @return chat page
     */
    @PostMapping("/add-message")
    public ModelAndView addMessage(@RequestParam(name = "message")String message, Model model, ModelMap modelMap)
    {
        //redirecting to landing page if not logged in
        if(!sessionBean.isLoggedIn()) {
            return new ModelAndView("login");
        }
        message = message.trim();
        if(message.length() == 0)
            model.addAttribute("error", "Cannot send blank messages");

        else {
            Message msg = new Message(sessionBean.getUserName(), message);
            getMessageRepo().save(msg);
        }

        return new ModelAndView("redirect:/chatroom", modelMap);
    }

    //we want to create the user again since we've deleted it because 10 seconds have passed
    //and the user os no longer exist in the database
    public void checkUserOnline(){

        User temp = getUserRepo().findByUserName(sessionBean.getUserName());
        if(temp == null) {      //if the user has deleted already from the db (10 seconds have passed),
            // we are creating it again
            User user = new User(sessionBean.getUserName(), System.currentTimeMillis());
            getUserRepo().save(user);
        }
    }
}
