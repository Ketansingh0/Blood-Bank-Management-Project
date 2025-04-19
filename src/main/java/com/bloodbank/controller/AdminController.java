package com.bloodbank.controller;

import com.bloodbank.model.BloodInventory;
import com.bloodbank.repository.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BloodInventoryRepository inventoryRepository;

    // Admin dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    // View Inventory List
    @GetMapping("/inventory")
    public String viewInventory(Model model) {
        model.addAttribute("inventoryList", inventoryRepository.findAll());
        return "admin/inventory";
    }

    // Show Add Form
    @GetMapping("/inventory/add")
    public String showAddInventoryForm(Model model) {
        model.addAttribute("bloodInventory", new BloodInventory());
        return "admin/add-inventory";
    }

    // Handle Add Form
    @PostMapping("/inventory/add")
    public String addInventory(@ModelAttribute BloodInventory bloodInventory) {
        bloodInventory.setLastUpdated(LocalDateTime.now());
        inventoryRepository.save(bloodInventory);
        return "redirect:/admin/inventory";
    }

    // Show Edit Form
    @GetMapping("/inventory/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BloodInventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid inventory ID: " + id));
        model.addAttribute("bloodInventory", inventory);
        return "admin/edit-inventory";
    }

    // Handle Edit Submission
    @PostMapping("/inventory/edit")
    public String updateInventory(@ModelAttribute BloodInventory bloodInventory) {
        bloodInventory.setLastUpdated(LocalDateTime.now());
        inventoryRepository.save(bloodInventory);
        return "redirect:/admin/inventory";
    }

    // Delete Inventory
    @GetMapping("/inventory/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
        return "redirect:/admin/inventory";
    }
}
