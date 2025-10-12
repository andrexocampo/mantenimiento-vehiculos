/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
