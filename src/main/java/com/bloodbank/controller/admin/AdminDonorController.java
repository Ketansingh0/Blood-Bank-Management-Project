package com.bloodbank.controller.admin;

import com.bloodbank.model.Donor;
import com.bloodbank.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin/donors")
public class AdminDonorController {

    @Autowired
    private DonorRepository donorRepository;

    // View all donors
    // View all donors
    @GetMapping
    public String viewAllDonors(Model model) {
        model.addAttribute("donors", donorRepository.findAll());
        return "admin/donor/donors";
    }


    // Show add donor form
    @GetMapping("/add-donor")
    public String addDonorForm(Model model) {
        model.addAttribute("donor", new Donor());
        return "admin/donor/add-donor";
    }

    // Save new donor
    @PostMapping("/add-donor")
    public String saveDonor(@ModelAttribute Donor donor) {
        donorRepository.save(donor);
        return "redirect:/admin/donors";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editDonorForm(@PathVariable Long id, Model model) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donor not found"));

        model.addAttribute("donor", donor);
        return "admin/donor/edit-donor";
    }

    // Update donor
    @PostMapping("/update")
    public String updateDonor(@ModelAttribute Donor donor) {
        donorRepository.save(donor);
        return "redirect:/admin/donors";
    }

    // Delete donor
    @GetMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        if (!donorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Donor not found");
        }
        donorRepository.deleteById(id);
        return "redirect:/admin/donors";
    }
}
