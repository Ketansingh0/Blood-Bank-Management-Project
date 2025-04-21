package com.bloodbank.controller.admin;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.repository.BloodRequestRepository;
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

    @GetMapping
    public String viewRequests(Model model) {
        List<BloodRequest> requests = bloodRequestRepository.findAll();
        model.addAttribute("requests", requests);
        return "admin/request/requests";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        BloodRequest request = bloodRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            bloodRequestRepository.save(request);
        }
        return "redirect:/admin/requests";
    }
}
