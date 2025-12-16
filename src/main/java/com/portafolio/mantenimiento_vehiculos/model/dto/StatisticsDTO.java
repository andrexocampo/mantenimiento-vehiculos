package com.portafolio.mantenimiento_vehiculos.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO containing all statistics for the current user
 * @author Andres
 */
public class StatisticsDTO {
    private long totalVehicles;
    private long totalPendingMaintenances;
    private long totalCompletedMaintenances;
    private List<VehicleCostDTO> vehicleCosts = new ArrayList<>();
    private float totalCosts;
    private float pendingCostsThisMonth;
    private List<MonthlyExpenseDTO> monthlyExpenses = new ArrayList<>();
    private QuarterlySummaryDTO quarterlySummary;
    
    // Getters and Setters
    public long getTotalVehicles() {
        return totalVehicles;
    }
    
    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }
    
    public long getTotalPendingMaintenances() {
        return totalPendingMaintenances;
    }
    
    public void setTotalPendingMaintenances(long totalPendingMaintenances) {
        this.totalPendingMaintenances = totalPendingMaintenances;
    }
    
    public long getTotalCompletedMaintenances() {
        return totalCompletedMaintenances;
    }
    
    public void setTotalCompletedMaintenances(long totalCompletedMaintenances) {
        this.totalCompletedMaintenances = totalCompletedMaintenances;
    }
    
    public List<VehicleCostDTO> getVehicleCosts() {
        return vehicleCosts;
    }
    
    public void setVehicleCosts(List<VehicleCostDTO> vehicleCosts) {
        this.vehicleCosts = vehicleCosts;
    }
    
    public float getTotalCosts() {
        return totalCosts;
    }
    
    public void setTotalCosts(float totalCosts) {
        this.totalCosts = totalCosts;
    }
    
    public float getPendingCostsThisMonth() {
        return pendingCostsThisMonth;
    }
    
    public void setPendingCostsThisMonth(float pendingCostsThisMonth) {
        this.pendingCostsThisMonth = pendingCostsThisMonth;
    }
    
    public List<MonthlyExpenseDTO> getMonthlyExpenses() {
        return monthlyExpenses;
    }
    
    public void setMonthlyExpenses(List<MonthlyExpenseDTO> monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }
    
    public QuarterlySummaryDTO getQuarterlySummary() {
        return quarterlySummary;
    }
    
    public void setQuarterlySummary(QuarterlySummaryDTO quarterlySummary) {
        this.quarterlySummary = quarterlySummary;
    }
}

