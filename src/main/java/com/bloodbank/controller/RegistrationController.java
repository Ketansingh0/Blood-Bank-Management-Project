package com.bloodbank.controller;

import com.bloodbank.model.User;
import com.bloodbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/register")
public String showRegistrationForm() {
    return "register"; // This should load register.html
}


  @PostMapping("/register")
  public String registerUser(@RequestParam String email, @RequestParam String password) {
    if (userRepository.findByEmail(email).isPresent()) {
      return "redirect:/register?error"; // User already exists
    }

    User user = User.builder()
        .email(email)
        .password(passwordEncoder.encode(password)) // 🔐 hashed!
        .role("ROLE_USER")
        .build();

    userRepository.save(user);
    return "redirect:/login?registered"; // ✅ success
  }

}
