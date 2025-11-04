/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfazMantenimientoService;
import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.ArrayList;
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
 *
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
        List<Vehiculo>vehiculos=service.listar();
        model.addAttribute("vehiculos",vehiculos);
        return "index";
        
    }
    
    @GetMapping("/vehiculos/listar/{id}")
    public String listarMantenimiento(Model model,@PathVariable int id){
        List<Mantenimiento>mantenimiento=serviceM.listar();
        model.addAttribute("mantenimientos",mantenimiento);
        return "index";
        
    }
    
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model){
        List<Vehiculo>vehiculos=service.listar();
        model.addAttribute("vehiculos",vehiculos);
        return "inventarioVehiculos";
        
    }
    
    // CRUD Vehiculos
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
    public String editar(@PathVariable int id,Model model){
        Optional<Vehiculo> vehiculo=service.listarId(id);
        model.addAttribute("vehiculo",vehiculo);
        return "form";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id , Model model){
        service.delete(id);
        return "redirect:/vehiculos";
    }
    
    // CRUD Mantenimientos
    @GetMapping("/nuevo_mantenimiento/{id}")
    public String agregarMantenimiento(Model model,@PathVariable int vehiculo_id){
        model.addAttribute("mantenimiento", new Mantenimiento());
        return "form_mantenimientos";
    }
    
    @PostMapping("/guardar_mantenimiento")
    public String guardarMantenimiento(Mantenimiento m, Model model){
        serviceM.save(m);
        return "redirect:/vehiculos";
    }
    
    @GetMapping("/editar_mantenimiento/{id}")
    public String editarMantenimiento(@PathVariable int id,Model model){
        Optional<Mantenimiento> mantenimiento=serviceM.listarId(id);
        model.addAttribute("mantenimiento",mantenimiento);
        return "form_mantenimientos";
    }
    
    @GetMapping("/eliminar_mantenimiento/{id}")
    public String eliminarMantenimietno(@PathVariable int id , Model model){
        serviceM.delete(id);
        return "redirect:/vehiculos";
    }
}
