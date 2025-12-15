package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfazMantenimientoService;
import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
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
 * Main controller for handling HTTP requests
 * @author Andres
 */
@Controller
@RequestMapping
public class Controlador {
    
    @Autowired
    private InterfaceVehiculoService service;
    
    @Autowired
    private InterfazMantenimientoService serviceM;
    
    @GetMapping("/listar")
    public String listar(Model model){
        List<Vehiculo> vehicles = service.listar();
        model.addAttribute("vehiculos", vehicles);
        return "index";
    }
    
    @GetMapping("/vehiculos/listar/{id}")
    public String listarMantenimiento(Model model, @PathVariable int id){
        // Get all maintenance records
        List<Mantenimiento> allMaintenances = serviceM.listar();
        // Get vehicle data by id
        Optional<Vehiculo> vehicleOptional = service.listarId(id);
        Vehiculo vehicle = vehicleOptional.get();
        // Get maintenance records for this vehicle
        List<Mantenimiento> vehicleMaintenances = service.listarMantenimientos(id);
        
        model.addAttribute("mantenimientosVehiculo", vehicleMaintenances);
        model.addAttribute("mantenimientos", allMaintenances);
        model.addAttribute("vehiculo", vehicle);
        return "index";
    }
    
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model){
        List<Vehiculo> vehicles = service.listar();
        model.addAttribute("vehiculos", vehicles);
        return "inventarioVehiculos";
    }
    
    // CRUD Vehicles
    @GetMapping("/new")
    public String agregar(Model model){
        model.addAttribute("vehiculo", new Vehiculo());
        return "form";
    }
    
    @PostMapping("/save")
    public String save(Vehiculo v, Model model){
        service.save(v);
        return "redirect:/vehiculos";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model){
        Optional<Vehiculo> vehicle = service.listarId(id);
        model.addAttribute("vehiculo", vehicle);
        return "form";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, Model model){
        service.delete(id);
        return "redirect:/vehiculos";
    }
    
    // CRUD Maintenance
    @GetMapping("/nuevo_mantenimiento/{id}")
    public String agregarMantenimiento(@PathVariable int id, Model model){
        Optional<Vehiculo> vehicleOptional = service.listarId(id);
        Vehiculo vehicle = vehicleOptional.get();
        model.addAttribute("vehiculo", vehicle);
        model.addAttribute("mantenimiento", new Mantenimiento());
        return "form_mantenimientos";
    }
    
    @PostMapping("/vehiculos/listar/{id}/guardar_mantenimiento")
    public String guardarMantenimiento(Mantenimiento m, Model model, @PathVariable int id){
        model.addAttribute("id", id);
        Optional<Vehiculo> vehicleOptional = service.listarId(id);
        Vehiculo vehicle = vehicleOptional.get();
        m.setVehicle(vehicle);
        serviceM.save(m);
        return "redirect:/vehiculos/listar/{id}";
    }
    
    @GetMapping("/editar_mantenimiento/{id}")
    public String editarMantenimiento(@PathVariable int id, Model model){
        Optional<Mantenimiento> maintenance = serviceM.listarId(id);
        model.addAttribute("mantenimiento", maintenance);
        return "form_mantenimientos";
    }
    
    @GetMapping("/eliminar_mantenimiento/{id}")
    public String eliminarMantenimiento(@PathVariable int id, Model model){
        serviceM.delete(id);
        return "redirect:/vehiculos";
    }
}
