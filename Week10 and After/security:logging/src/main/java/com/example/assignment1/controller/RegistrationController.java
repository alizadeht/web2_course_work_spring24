package com.example.assignment1.controller;

import com.example.assignment1.model.dto.SignupDto;
import com.example.assignment1.model.entity.User;
import com.example.assignment1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

/**
 * Controller class for user registration.
 */
@Controller
@RequestMapping("/signup")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Displays the sign-up form.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name.
     */

    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("signupDto", new SignupDto());
        return "registration";
    }

    /**
     * Processes the form submission to register a new user.
     *
     * @param signupDto          The SignupDto object containing user registration data.
     * @param result             The BindingResult object for validation.
     * @param redirectAttributes The RedirectAttributes object for flash messages.
     * @param model              The Model object for passing data to the view.
     * @return The redirection URL.
     */
    @PostMapping
    public String registerNewUser(@ModelAttribute("signupDto") @Valid SignupDto signupDto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "registration"; // Stay on the registration page and show errors
        }

        // If no errors, proceed with user creation
        User newUser = signupDto.toUser(passwordEncoder);
        newUser.addRole("ROLE_USER"); // Default role
        userRepository.save(newUser);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
        return "redirect:/login";
    }

}
