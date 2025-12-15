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

/**
 * Main controller for handling HTTP requests with RESTful structure
 * @author Andres
 */
@Controller
@RequestMapping
public class VehicleController {
    
    @Autowired
    private VehicleServiceInterface service;
    
    @Autowired
    private MaintenanceServiceInterface serviceM;
    
    // ========== HOME / ROOT ==========
    
    @GetMapping("/")
    public String home() {
        return "redirect:/vehicles";
    }
    
    // ========== VEHICLES CRUD ==========
    
    @GetMapping("/vehicles")
    public String listVehicles(Model model) {
        List<Vehicle> vehicles = service.listar();
        model.addAttribute("vehiculos", vehicles);
        return "vehicle_inventory";
    }
    
    @GetMapping("/vehicles/new")
    public String showVehicleForm(Model model) {
        model.addAttribute("vehiculo", new Vehicle());
        return "vehicle_form";
    }
    
    @PostMapping("/vehicles")
    public String createVehicle(Vehicle v) {
        service.save(v);
        return "redirect:/vehicles";
    }
    
    @GetMapping("/vehicles/{id}")
    public String viewVehicle(@PathVariable int id, Model model) {
        Optional<Vehicle> vehicleOptional = service.listarId(id);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        Vehicle vehicle = vehicleOptional.get();
        List<Maintenance> vehicleMaintenances = service.listarMantenimientos(id);
        List<Maintenance> allMaintenances = serviceM.listar();
        
        model.addAttribute("vehiculo", vehicle);
        model.addAttribute("mantenimientosVehiculo", vehicleMaintenances);
        model.addAttribute("mantenimientos", allMaintenances);
        return "vehicle_details";
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
        Optional<Vehicle> existing = service.listarId(id);
        if (existing.isPresent()) {
            v.setId(id);
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
    
    // ========== MAINTENANCES CRUD (nested under vehicles) ==========
    
    @GetMapping("/vehicles/{vehicleId}/maintenances")
    public String listMaintenances(@PathVariable int vehicleId, Model model) {
        Optional<Vehicle> vehicleOptional = service.listarId(vehicleId);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        Vehicle vehicle = vehicleOptional.get();
        List<Maintenance> maintenances = service.listarMantenimientos(vehicleId);
        List<Maintenance> allMaintenances = serviceM.listar();
        
        model.addAttribute("vehiculo", vehicle);
        model.addAttribute("mantenimientosVehiculo", maintenances);
        model.addAttribute("mantenimientos", allMaintenances);
        return "vehicle_details";
    }
    
    @GetMapping("/vehicles/{vehicleId}/maintenances/new")
    public String showMaintenanceForm(@PathVariable int vehicleId, Model model) {
        Optional<Vehicle> vehicleOptional = service.listarId(vehicleId);
        if (vehicleOptional.isEmpty()) {
            return "redirect:/vehicles";
        }
        model.addAttribute("vehiculo", vehicleOptional.get());
        model.addAttribute("mantenimiento", new Maintenance());
        return "maintenance_form";
    }
    
    @PostMapping("/vehicles/{vehicleId}/maintenances")
    public String createMaintenance(@PathVariable int vehicleId, Maintenance m) {
        Optional<Vehicle> vehicleOptional = service.listarId(vehicleId);
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
        Optional<Maintenance> maintenance = serviceM.listarId(id);
        if (maintenance.isEmpty()) {
            return "redirect:/vehicles/" + vehicleId;
        }
        Maintenance m = maintenance.get();
        // Verify that the maintenance belongs to the vehicle
        if (m.getVehicle().getId() != vehicleId) {
            return "redirect:/vehicles/" + vehicleId;
        }
        model.addAttribute("mantenimiento", m);
        model.addAttribute("vehiculo", m.getVehicle());
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
            return "redirect:/vehicles/" + vehicleId;
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

