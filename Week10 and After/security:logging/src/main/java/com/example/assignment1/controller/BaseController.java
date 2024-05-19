package com.example.assignment1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling base requests.
 */
@Controller
public class BaseController {

    @Autowired
    @Qualifier("greetText")
    private String welcomeMessage;

    @Autowired
    @Qualifier("byeText")
    private String farewellMessage;

    /**
     * Handles GET requests to the root ("/") URL.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name.
     */
    @GetMapping("/")
    public String getWelcomePage(Model model){
        model.addAttribute("message", " This is global HOME page");
        return "welcome";
    }

    /**
     * Handles GET requests to the "/bye" URL.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name.
     */
    @GetMapping("/bye")
    public String getFarewellPage(Model model){
        model.addAttribute("message", farewellMessage);
        return "welcome";
    }

}