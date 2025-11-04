/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
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
 *
 * @author Andres
 */
@Controller
@RequestMapping
public class Controlador {
    
    @Autowired
    private InterfaceVehiculoService service;
    
    @GetMapping("/listar")
    public String listar(Model model){
        List<Vehiculo>vehiculos=service.listar();
        model.addAttribute("vehiculos",vehiculos);
        return "index";
        
    }
    
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model){
        List<Vehiculo>vehiculos=service.listar();
        model.addAttribute("vehiculos",vehiculos);
        return "inventarioVehiculos";
        
    }
    
    @GetMapping("/new")
    public String agregar(Model model){
        model.addAttribute("vehiculo", new Vehiculo());
        return "form";
    }
    
    @PostMapping("/save")
    public String save(Vehiculo v, Model model){
        service.save(v);
        return "redirect:/listar";
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
        return "redirect:/listar";
    }
    
}
