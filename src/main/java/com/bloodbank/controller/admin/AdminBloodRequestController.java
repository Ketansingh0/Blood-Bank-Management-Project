package com.bloodbank.controller.admin;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.repository.BloodRequestRepository;
import com.bloodbank.service.EmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/requests")
@RequiredArgsConstructor
public class AdminBloodRequestController {

    private final BloodRequestRepository bloodRequestRepository;
    private final EmailService emailService;

    @GetMapping
    public String viewRequests(@RequestParam(required = false) String status,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String bloodGroup,
                               Model model) {

        List<BloodRequest> requests = bloodRequestRepository.searchRequests(status, email, bloodGroup);
        model.addAttribute("requests", requests);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedEmail", email);
        model.addAttribute("selectedBloodGroup", bloodGroup);

        return "admin/request/requests";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        BloodRequest request = bloodRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            bloodRequestRepository.save(request);

            if (request.getUser() != null && request.getUser().getEmail() != null) {
                emailService.sendStatusUpdateEmail(request.getUser().getEmail(), status);
            }
        }
        return "redirect:/admin/requests";
    }
}
