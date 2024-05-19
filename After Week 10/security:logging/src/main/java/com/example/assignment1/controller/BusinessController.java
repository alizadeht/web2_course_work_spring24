package com.example.assignment1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling business-related requests.
 */
@Controller
public class BusinessController {

    /**
     * Handles GET requests to the "/users" URL.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name.
     */
    @GetMapping("/users")
    public String getUsersHomePage(Model model){
        model.addAttribute("message", "This is home for USERS");
        return "welcome";
    }

    /**
     * Handles GET requests to the "/admins" URL.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name.
     */
    @GetMapping("/admins")
    public String getAdminsHomePage(Model model){
        model.addAttribute("message", "This is home for ADMINS");
        return "welcome";
    }
}
