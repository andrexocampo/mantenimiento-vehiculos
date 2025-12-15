package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfacesService.MaintenanceServiceInterface;
import com.portafolio.mantenimiento_vehiculos.interfacesService.VehicleServiceInterface;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling maintenance-related requests
 * @author Andres
 */
@Controller
@RequestMapping
public class MaintenanceController {
    
    @Autowired
    private MaintenanceServiceInterface serviceM;
    
    @Autowired
    private VehicleServiceInterface vehicleService;
    
    // ========== MAINTENANCES OVERVIEW ==========
    
    /**
     * Display overview of all maintenances for the current user
     * Shows pending and completed maintenances in separate tables
     */
    @GetMapping("/maintenances")
    public String listMaintenancesOverview(Model model) {
        try {
            List<Maintenance> pendingMaintenances = serviceM.listPendingMaintenances();
            List<Maintenance> completedMaintenances = serviceM.listCompletedMaintenances();
            
            model.addAttribute("pendingMaintenances", pendingMaintenances);
            model.addAttribute("completedMaintenances", completedMaintenances);
            return "maintenances_overview";
        } catch (Exception e) {
            System.err.println("Error loading maintenances overview: " + e.getMessage());
            model.addAttribute("error", "Error loading maintenances: " + e.getMessage());
            return "redirect:/vehicles";
        }
    }
    
    @PostMapping("/maintenances/{id}/mark-paid")
    public String markAsPaid(@PathVariable int id, 
                             @RequestParam(value = "actualCost", required = false) Float actualCost) {
        serviceM.markAsPaid(id, actualCost);
        return "redirect:/maintenances";
    }
    
    @PostMapping("/maintenances/{id}/revert-payment")
    public String revertPayment(@PathVariable int id) {
        serviceM.revertPayment(id);
        return "redirect:/maintenances";
    }
    
    // ========== MAINTENANCES CRUD (nested under vehicles) ==========
    
    @GetMapping("/vehicles/{vehicleId}/maintenances")
    public String listMaintenances(@PathVariable int vehicleId, Model model) {
        Optional<Vehicle> vehicleOptional = vehicleService.listarId(vehicleId);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        Vehicle vehicle = vehicleOptional.get();
        List<Maintenance> maintenances = vehicleService.listarMantenimientos(vehicleId);
        
        model.addAttribute("vehiculo", vehicle);
        model.addAttribute("mantenimientosVehiculo", maintenances);
        return "vehicle_details";
    }
    
    @GetMapping("/vehicles/{vehicleId}/maintenances/new")
    public String showMaintenanceForm(@PathVariable int vehicleId, Model model) {
        Optional<Vehicle> vehicleOptional = vehicleService.listarId(vehicleId);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        model.addAttribute("vehiculo", vehicleOptional.get());
        model.addAttribute("mantenimiento", new Maintenance());
        return "maintenance_form";
    }
    
    @PostMapping("/vehicles/{vehicleId}/maintenances")
    public String createMaintenance(@PathVariable int vehicleId, Maintenance m) {
        Optional<Vehicle> vehicleOptional = vehicleService.listarId(vehicleId);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        m.setVehicle(vehicleOptional.get());
        serviceM.save(m);
        return "redirect:/vehicles/" + vehicleId;
    }
    
    @GetMapping("/vehicles/{vehicleId}/maintenances/{id}/edit")
    public String editMaintenanceForm(@PathVariable int vehicleId, 
                                      @PathVariable int id, Model model) {
        Optional<Maintenance> maintenanceOptional = serviceM.listarId(id);
        if (maintenanceOptional.isEmpty()) {
            return "redirect:/vehicles/" + vehicleId;
        }
        Maintenance maintenance = maintenanceOptional.get();
        // Verify that the maintenance belongs to the vehicle
        if (maintenance.getVehicle().getId() != vehicleId) {
            return "redirect:/vehicles/" + vehicleId;
        }
        model.addAttribute("mantenimiento", maintenance);
        model.addAttribute("vehiculo", maintenance.getVehicle());
        return "maintenance_form";
    }
    
    @PostMapping("/vehicles/{vehicleId}/maintenances/{id}")
    public String updateMaintenance(@PathVariable int vehicleId, 
                                   @PathVariable int id, Maintenance m) {
        Optional<Maintenance> existing = serviceM.listarId(id);
        if (existing.isPresent()) {
            Maintenance existingMaintenance = existing.get();
            // Verify that the maintenance belongs to the vehicle
            if (existingMaintenance.getVehicle().getId() != vehicleId) {
                return "redirect:/vehicles/" + vehicleId;
            }
            m.setId(id);
            m.setVehicle(existingMaintenance.getVehicle());
            serviceM.save(m);
        }
        return "redirect:/vehicles/" + vehicleId;
    }
    
    @GetMapping("/vehicles/{vehicleId}/maintenances/{id}/delete")
    public String deleteMaintenance(@PathVariable int vehicleId, @PathVariable int id) {
        Optional<Maintenance> maintenance = serviceM.listarId(id);
        if (maintenance.isPresent()) {
            // Verify that the maintenance belongs to the vehicle
            if (maintenance.get().getVehicle().getId() == vehicleId) {
                serviceM.delete(id);
            }
        }
        return "redirect:/vehicles/" + vehicleId;
    }
}

