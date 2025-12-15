package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfacesService.VehicleServiceInterface;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import com.portafolio.mantenimiento_vehiculos.model.dto.NotificationResult;
import com.portafolio.mantenimiento_vehiculos.service.NotificationService;
import com.portafolio.mantenimiento_vehiculos.service.SecurityService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling vehicle-related requests
 * @author Andres
 */
@Controller
@RequestMapping
public class VehicleController {
    
    @Autowired
    private VehicleServiceInterface service;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private SecurityService securityService;
    
    // ========== HOME / ROOT ==========
    
    @GetMapping("/")
    public String home() {
        return "redirect:/vehicles";
    }
    
    // ========== VEHICLES CRUD ==========
    
    @GetMapping("/vehicles")
    public String listVehicles(Model model) {
        try {
            List<Vehicle> vehicles = service.listar();
            model.addAttribute("vehiculos", vehicles);
            
            // Check for maintenance notifications (Option 1: Check on login)
            try {
                com.portafolio.mantenimiento_vehiculos.model.User currentUser = securityService.getCurrentUser();
                NotificationResult notifications = notificationService.checkMaintenances(currentUser);
                model.addAttribute("notifications", notifications);
            } catch (Exception e) {
                // If notification check fails, continue without notifications
                System.err.println("Error checking notifications: " + e.getMessage());
            }
            
            return "vehicle_inventory";
        } catch (Exception e) {
            System.err.println("Error listing vehicles: " + e.getMessage());
            model.addAttribute("error", "Error loading vehicles: " + e.getMessage());
            return "vehicle_inventory";
        }
    }
    
    @GetMapping("/vehicles/new")
    public String showVehicleForm(Model model) {
        model.addAttribute("vehiculo", new Vehicle());
        return "vehicle_form";
    }
    
    @PostMapping("/vehicles")
    public String createVehicle(Vehicle v) {
        // User assignment is now handled in VehicleService.save(v)
        service.save(v);
        return "redirect:/vehicles";
    }
    
    @GetMapping("/vehicles/{id}")
    public String viewVehicle(@PathVariable int id, Model model) {
        try {
            Optional<Vehicle> vehicleOptional = service.listarId(id);
            if (vehicleOptional.isEmpty()) {
                return "redirect:/vehicles"; // Vehicle not found or not owned by user
            }
            Vehicle vehicle = vehicleOptional.get();
            List<Maintenance> vehicleMaintenances = service.listarMantenimientos(id);
            
            model.addAttribute("vehiculo", vehicle);
            model.addAttribute("mantenimientosVehiculo", vehicleMaintenances);
            return "vehicle_details";
        } catch (Exception e) {
            System.err.println("Error viewing vehicle details: " + e.getMessage());
            model.addAttribute("error", "Error loading vehicle details: " + e.getMessage());
            return "redirect:/vehicles";
        }
    }
    
    @GetMapping("/vehicles/{id}/edit")
    public String editVehicleForm(@PathVariable int id, Model model) {
        Optional<Vehicle> vehicleOptional = service.listarId(id);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        model.addAttribute("vehiculo", vehicleOptional.get());
        return "vehicle_form";
    }
    
    @PostMapping("/vehicles/{id}")
    public String updateVehicle(@PathVariable int id, Vehicle v) {
        Optional<Vehicle> existing = service.listarId(id); // Use listarId to ensure ownership
        if (existing.isPresent()) {
            v.setId(id);
            v.setUser(existing.get().getUser()); // Ensure user is preserved
            service.save(v);
            return "redirect:/vehicles/" + id;
        }
        return "redirect:/vehicles";
    }
    
    @GetMapping("/vehicles/{id}/delete")
    public String deleteVehicle(@PathVariable int id) {
        service.delete(id);
        return "redirect:/vehicles";
    }
}

