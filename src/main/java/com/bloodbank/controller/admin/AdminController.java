package com.bloodbank.controller.admin;

import com.bloodbank.model.BloodInventory;
import com.bloodbank.model.User;
import com.bloodbank.repository.BloodInventoryRepository;
import com.bloodbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalUnits = bloodInventoryRepository.findAll()
                .stream()
                .mapToLong(BloodInventory::getUnit)
                .sum();

        long totalDonors = userRepository.countByRole("ROLE_USER");
        long adminCount = userRepository.countByRole("ROLE_ADMIN");

        model.addAttribute("totalUnits", totalUnits);
        model.addAttribute("totalDonors", totalDonors);
        model.addAttribute("adminCount", adminCount);
        return "admin/dashboard";
    }

    // Show all donors
    @GetMapping("/admin/all-donors")
    public String viewDonors(Model model) {
        List<User> donorList = userRepository.findByRole("ROLE_USER");
        model.addAttribute("donorList", donorList);
        return "admin/donors";
    }

    // Show Blood Inventory List
    @GetMapping("/inventory")
    public String inventoryPage(Model model) {
        List<BloodInventory> bloodList = bloodInventoryRepository.findAll();
        model.addAttribute("bloodList", bloodList);
        return "admin/inventory";
    }

    // Add Blood Inventory
    @GetMapping("/inventory/add")
    public String showAddForm(Model model) {
        model.addAttribute("blood", new BloodInventory());
        return "admin/add-inventory";
    }

    @PostMapping("/inventory/add")
    public String addBlood(@ModelAttribute BloodInventory blood) {
        bloodInventoryRepository.save(blood);
        return "redirect:/admin/inventory";
    }

    // Edit Blood Inventory
    @GetMapping("/inventory/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BloodInventory blood = bloodInventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("blood", blood);
        return "admin/edit-inventory";
    }

    @PostMapping("/inventory/update")
    public String updateBlood(@ModelAttribute BloodInventory blood) {
        bloodInventoryRepository.save(blood);
        return "redirect:/admin/inventory";
    }

    // Delete Blood Unit
    @GetMapping("/inventory/delete/{id}")
    public String deleteBlood(@PathVariable Long id) {
        bloodInventoryRepository.deleteById(id);
        return "redirect:/admin/inventory";
    }
}
