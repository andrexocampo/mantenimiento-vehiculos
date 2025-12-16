package com.portafolio.mantenimiento_vehiculos.controller;

import com.portafolio.mantenimiento_vehiculos.model.dto.StatisticsDTO;
import com.portafolio.mantenimiento_vehiculos.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling statistics requests
 * @author Andres
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    @GetMapping
    public String showStatistics(Model model) {
        try {
            StatisticsDTO stats = statisticsService.getStatistics();
            model.addAttribute("statistics", stats);
            return "statistics";
        } catch (Exception e) {
            System.err.println("Error loading statistics: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading statistics: " + e.getMessage());
            return "redirect:/vehicles";
        }
    }
}



