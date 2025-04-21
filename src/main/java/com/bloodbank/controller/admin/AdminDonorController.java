package com.bloodbank.controller.admin;

import com.bloodbank.model.Donor;
import com.bloodbank.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/donors")
public class AdminDonorController {

    @Autowired
    private DonorRepository donorRepository;

    @GetMapping
    public String viewAllDonors(Model model) {
        model.addAttribute("donorList", donorRepository.findAll());
        return "admin/donor/donors";
    }

    @GetMapping("/add")
    public String addDonorForm(Model model) {
        model.addAttribute("donor", new Donor());
        return "admin/donor/add-donor";
    }

    @PostMapping("/add")
    public String saveDonor(@ModelAttribute Donor donor) {
        donorRepository.save(donor);
        return "redirect:/admin/donors";
    }

    @GetMapping("/edit/{id}")
    public String editDonorForm(@PathVariable Long id, Model model) {
        Donor donor = donorRepository.findById(id).orElseThrow();
        model.addAttribute("donor", donor);
        return "admin/donor/edit-donor";
    }

    @PostMapping("/update")
    public String updateDonor(@ModelAttribute Donor donor) {
        donorRepository.save(donor);
        return "redirect:/admin/donors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        donorRepository.deleteById(id);
        return "redirect:/admin/donors";
    }
}
