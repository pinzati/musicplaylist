package hh.sof03.musicplaylist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import hh.sof03.musicplaylist.domain.User;
import hh.sof03.musicplaylist.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }
/*
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error",
                    "Username already exists. Please choose a different username.");
            return "redirect:/register";
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("error", "Email already exists. Please use a different email.");
            return "redirect:/register";
        } else if (user.getUsername().length() < 3 || user.getUsername().length() > 20) {
            redirectAttributes.addFlashAttribute("error", "Username must be between 3 and 20 characters long.");
            return "redirect:/register";
        } else if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPasswordHash().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please fill in all fields.");
            return "redirect:/register";
        } else if (user.getPasswordHash().length() < 3) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 3 characters long.");
            return "redirect:/register";
        } else {
            user.setRole("USER");
            String encodePassword = passwordEncoder.encode(user.getPasswordHash());
            user.setPasswordHash(encodePassword);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("registrationSuccess", true);
            return "redirect:/login";
        }
    } */

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration"; // Return to registration page if there are validation errors
        } else {
            // Your existing registration logic
            user.setRole("USER");
            String encodePassword = passwordEncoder.encode(user.getPasswordHash());
            user.setPasswordHash(encodePassword);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("registrationSuccess", true);
            return "redirect:/login";
        }
    }

    /*
     * @PostMapping("/register")
     * public String registerUser(@ModelAttribute("user") @Valid User user,
     * BindingResult bindingResult, RedirectAttributes redirectAttributes) {
     * 
     * if (bindingResult.hasErrors()) {
     * 
     * return "registration";
     * } else {
     * 
     * userRepository.save(user);
     * redirectAttributes.addFlashAttribute("registrationSuccess", true);
     * return "redirect:/login";
     * }
     * }
     */

    @ModelAttribute("currentUser")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

   /* @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        model.addAttribute("username", username);

        return "profile";
    }*/

}
