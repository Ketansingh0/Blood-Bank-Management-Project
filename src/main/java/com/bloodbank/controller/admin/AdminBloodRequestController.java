
package com.bloodbank.controller.admin;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.repository.BloodRequestRepository;
import com.bloodbank.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/requests")
@RequiredArgsConstructor
public class AdminBloodRequestController {

    private final BloodRequestRepository bloodRequestRepository;
    private final EmailService emailService;

    @GetMapping
    public String viewRequests(@RequestParam(required = false) String status,
                               @RequestParam(required = false) String email,
                               Model model) {

        List<BloodRequest> requests = bloodRequestRepository.findAll();

        if (status != null && !status.isEmpty()) {
            requests = requests.stream()
                    .filter(r -> r.getStatus() != null && r.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        if (email != null && !email.isEmpty()) {
            requests = requests.stream()
                    .filter(r -> r.getUser() != null && r.getUser().getEmail() != null &&
                            r.getUser().getEmail().toLowerCase().contains(email.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("requests", requests);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedEmail", email);

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
