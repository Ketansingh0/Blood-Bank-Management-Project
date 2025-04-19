
package com.bloodbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // loads index.html
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // loads home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // loads login.html
    }
}


