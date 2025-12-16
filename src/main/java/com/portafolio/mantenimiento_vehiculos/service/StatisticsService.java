package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.MaintenanceRepository;
import com.portafolio.mantenimiento_vehiculos.interfaces.VehicleRepository;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import com.portafolio.mantenimiento_vehiculos.model.dto.MonthlyExpenseDTO;
import com.portafolio.mantenimiento_vehiculos.model.dto.QuarterlySummaryDTO;
import com.portafolio.mantenimiento_vehiculos.model.dto.StatisticsDTO;
import com.portafolio.mantenimiento_vehiculos.model.dto.VehicleCostDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for calculating and providing statistics
 * @author Andres
 */
@Service
public class StatisticsService {
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final String[] MONTH_NAMES = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    
    /**
     * Get all statistics for the current user (last 12 months)
     */
    public StatisticsDTO getStatistics() {
        User currentUser = securityService.getCurrentUser();
        StatisticsDTO stats = new StatisticsDTO();
        
        // Calculate start date for last 12 months
        LocalDate twelveMonthsAgo = LocalDate.now().minusMonths(12);
        
        // 1. Total de vehículos (no cambia, es el total)
        List<Vehicle> vehicles = vehicleRepository.findByUser(currentUser);
        stats.setTotalVehicles(vehicles.size());
        
        // 2. Total de mantenimientos (pendientes son todos, completados son últimos 12 meses)
        List<Maintenance> pending = maintenanceRepository.findPendingByUser(currentUser);
        stats.setTotalPendingMaintenances(pending.size());
        
        // Completados de los últimos 12 meses
        Long completedLast12Months = maintenanceRepository.getCompletedMaintenancesLast12MonthsByUser(currentUser, twelveMonthsAgo);
        stats.setTotalCompletedMaintenances(completedLast12Months != null ? completedLast12Months.intValue() : 0);
        
        // 3. Costos totales de los últimos 12 meses (solo pagados)
        Float totalCostsLast12Months = maintenanceRepository.getTotalCostsLast12MonthsByUser(currentUser, twelveMonthsAgo);
        stats.setTotalCosts(totalCostsLast12Months != null ? totalCostsLast12Months : 0f);
        
        // 4. Costo de pagos pendientes de este mes
        Float pendingThisMonth = maintenanceRepository.getPendingCostsThisMonthByUser(currentUser);
        stats.setPendingCostsThisMonth(pendingThisMonth != null ? pendingThisMonth : 0f);
        
        // 5. Costo por vehículo (para gráfico de barras) - últimos 12 meses
        List<VehicleCostDTO> vehicleCosts = calculateVehicleCostsLast12Months(vehicles, twelveMonthsAgo);
        stats.setVehicleCosts(vehicleCosts);
        
        // 6. Gastos mensuales últimos 12 meses
        List<MonthlyExpenseDTO> monthlyExpenses = calculateMonthlyExpenses(currentUser, twelveMonthsAgo);
        stats.setMonthlyExpenses(monthlyExpenses);
        
        // 7. Resumen trimestral
        QuarterlySummaryDTO quarterlySummary = calculateQuarterlySummary(currentUser);
        stats.setQuarterlySummary(quarterlySummary);
        
        return stats;
    }
    
    /**
     * Calculate total costs per vehicle (only paid maintenances) in the last 12 months
     */
    private List<VehicleCostDTO> calculateVehicleCostsLast12Months(List<Vehicle> vehicles, LocalDate startDate) {
        List<VehicleCostDTO> vehicleCosts = new ArrayList<>();
        User currentUser = securityService.getCurrentUser();
        
        // Get aggregated costs from database for last 12 months
        List<Object[]> results = maintenanceRepository.getVehicleCostsLast12MonthsByUser(currentUser, startDate);
        Map<Integer, Float> costMap = new HashMap<>();
        
        for (Object[] result : results) {
            Vehicle vehicle = (Vehicle) result[0];
            Double totalCost = (Double) result[1];
            costMap.put(vehicle.getId(), totalCost.floatValue());
        }
        
        // Create DTOs for all vehicles (even if they have no costs)
        for (Vehicle vehicle : vehicles) {
            float totalCost = costMap.getOrDefault(vehicle.getId(), 0f);
            vehicleCosts.add(new VehicleCostDTO(
                vehicle.getName(),
                vehicle.getLicensePlate(),
                totalCost
            ));
        }
        
        return vehicleCosts;
    }
    
    /**
     * Calculate monthly expenses for the last 12 months
     * Fills in months with no expenses as 0
     */
    private List<MonthlyExpenseDTO> calculateMonthlyExpenses(User user, LocalDate startDate) {
        List<MonthlyExpenseDTO> monthlyExpenses = new ArrayList<>();
        
        // Get expenses from database
        List<Object[]> results = maintenanceRepository.getMonthlyExpensesByUser(user.getId(), startDate);
        Map<String, Float> expenseMap = new HashMap<>();
        
        for (Object[] result : results) {
            String month = (String) result[0];
            Double total = (Double) result[1];
            expenseMap.put(month, total.floatValue());
        }
        
        // Generate list for last 12 months, filling missing months with 0
        LocalDate current = startDate;
        LocalDate now = LocalDate.now();
        
        while (!current.isAfter(now)) {
            String monthKey = current.format(MONTH_FORMATTER);
            float expense = expenseMap.getOrDefault(monthKey, 0f);
            
            // Format month label
            String monthLabel = formatMonthLabel(current);
            
            monthlyExpenses.add(new MonthlyExpenseDTO(monthKey, monthLabel, expense));
            current = current.plusMonths(1);
        }
        
        return monthlyExpenses;
    }
    
    /**
     * Calculate quarterly summary for current quarter
     */
    private QuarterlySummaryDTO calculateQuarterlySummary(User user) {
        List<Object[]> results = maintenanceRepository.getCurrentQuarterSummaryByUser(user.getId());
        
        float totalExpense = 0f;
        int maintenanceCount = 0;
        
        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            totalExpense = ((Double) result[0]).floatValue();
            maintenanceCount = ((Long) result[1]).intValue();
        }
        
        // Determine current quarter
        LocalDate now = LocalDate.now();
        int quarter = (now.getMonthValue() - 1) / 3 + 1;
        String quarterLabel = "Q" + quarter + " " + now.getYear();
        
        return new QuarterlySummaryDTO(quarterLabel, totalExpense, maintenanceCount);
    }
    
    /**
     * Format month label (e.g., "January 2024")
     */
    private String formatMonthLabel(LocalDate date) {
        int monthIndex = date.getMonthValue() - 1;
        return MONTH_NAMES[monthIndex] + " " + date.getYear();
    }
}

