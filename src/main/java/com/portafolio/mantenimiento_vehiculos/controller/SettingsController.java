package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfaces.UserRepository;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling user settings and preferences
 * @author Andres
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String showSettings(Model model) {
        try {
            User currentUser = securityService.getCurrentUser();
            model.addAttribute("user", currentUser);
            return "settings";
        } catch (Exception e) {
            return "redirect:/vehicles";
        }
    }
    
    @PostMapping
    public String updateSettings(User updatedUser) {
        try {
            User currentUser = securityService.getCurrentUser();
            
            // Update only notification settings and email
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setEmailNotificationsEnabled(updatedUser.isEmailNotificationsEnabled());
            currentUser.setNotifyOneWeekBefore(updatedUser.isNotifyOneWeekBefore());
            currentUser.setNotifyOneDayBefore(updatedUser.isNotifyOneDayBefore());
            
            userRepository.save(currentUser);
            return "redirect:/settings?success=true";
        } catch (Exception e) {
            return "redirect:/settings?error=true";
        }
    }
}

