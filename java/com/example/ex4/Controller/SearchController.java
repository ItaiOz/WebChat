package com.example.ex4.Controller;

import com.example.ex4.beans.Logger;
import com.example.ex4.repo.MessageRepository;
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
 * Controller to handle search requests from users
 */
@Controller
public class SearchController {

    /**
     * Injecting repository here
     */
    @Autowired
    private MessageRepository messageRepository;

    private MessageRepository getMessageRepo() { return messageRepository;   }

    @Resource(name = "sessionBean")
    private Logger sessionBean;

    /**
     * Post request to handle searches from user
     * @param model
     * @return landing page if not logged in, search page if logged
     */
    @PostMapping("/search")
    public String getSearch(Model model) {
        if (!sessionBean.isLoggedIn()) {
            return "login";
        }
            model.addAttribute("choice", null);     //choice variable will indicate to present to search headline or not
            return "search";

    }

    /**
     * Get request
     * @param model
     * @return search page
     */
    @GetMapping("/search")
    public String foo(Model model){
        if(!sessionBean.isLoggedIn()) {
            return "login";
        }
        model.addAttribute("choice", null);
        return "search";
    }

    /**
     * Post request to return users list according to the word entered
     * @param input
     * @param model
     * @return result list with valid users
     */
    @PostMapping("/users-search")
    public String returnUsers(@RequestParam("value")String input, Model model){

            if(!sessionBean.isLoggedIn()) {
                return "login";
            }
        //sending data for users that found according to the keyword
        model.addAttribute("results", getMessageRepo().findByUserName(input));
        model.addAttribute("choice", "users");
        return "search";
    }

    @GetMapping("/users-search")
    public ModelAndView returnPage(ModelMap model){
        return new ModelAndView("redirect:/search", model);
    }

    /**
     * Post request to return ,essages list according to the word entered
     * @param input
     * @param model
     * @return result list with valid messages
     */
    @PostMapping("/messages-search")
    public String returnMessages(@RequestParam("value")String input, Model model){
        //returning to home page if not logged in
        if(!sessionBean.isLoggedIn()) {
            return "login";
        }
        //sending data that found according to the keywords
        model.addAttribute("results", getMessageRepo().findMessageByWord(input));
        model.addAttribute("choice", "messages");
        return "search";
    }
    @GetMapping("/messages-search")
    public ModelAndView returnPage2(ModelMap model){
        return new ModelAndView("redirect:/search", model);
    }

}
