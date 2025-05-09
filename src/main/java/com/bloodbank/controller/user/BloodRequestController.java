package com.bloodbank.controller.user;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.model.User;
import com.bloodbank.repository.BloodRequestRepository;
import com.bloodbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BloodRequestController {

    private final BloodRequestRepository bloodRequestRepository;
    private final UserRepository userRepository;

    // Show blood request form
    @GetMapping("/request-blood")
    public String showRequestForm(Model model) {
        model.addAttribute("bloodRequest", new BloodRequest());
        return "user/request-blood";
    }
    
    @PostMapping("/request-blood")
    public String submitBloodRequest(@Valid @ModelAttribute("bloodRequest") BloodRequest bloodRequest,
                                     BindingResult result,
                                     Principal principal,
                                     Model model) {
        if (result.hasErrors()) {
            return "user/request-blood";
        }
    
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);
    
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "user/request-blood";
        }
    
        bloodRequest.setUser(user);
        bloodRequest.setRequestDate(LocalDateTime.now());
        bloodRequest.setStatus("PENDING");
        bloodRequestRepository.save(bloodRequest);
    
        model.addAttribute("success", "Blood request submitted successfully!");
        model.addAttribute("bloodRequest", new BloodRequest()); // reset form
    
        return "user/request-blood";
    }
    

    // User dashboard: view past blood requests
    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        List<BloodRequest> requests = bloodRequestRepository.findByUser(user);
        model.addAttribute("requests", requests);
        return "user/dashboard";
    }
}
