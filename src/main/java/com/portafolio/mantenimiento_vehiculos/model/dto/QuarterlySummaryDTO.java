package com.portafolio.mantenimiento_vehiculos.model.dto;

/**
 * DTO for quarterly summary data
 * @author Andres
 */
public class QuarterlySummaryDTO {
    private String quarter; // "Q1 2024"
    private float totalExpense;
    private int maintenanceCount;
    
    public QuarterlySummaryDTO() {}
    
    public QuarterlySummaryDTO(String quarter, float totalExpense, int maintenanceCount) {
        this.quarter = quarter;
        this.totalExpense = totalExpense;
        this.maintenanceCount = maintenanceCount;
    }
    
    // Getters and Setters
    public String getQuarter() {
        return quarter;
    }
    
    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
    
    public float getTotalExpense() {
        return totalExpense;
    }
    
    public void setTotalExpense(float totalExpense) {
        this.totalExpense = totalExpense;
    }
    
    public int getMaintenanceCount() {
        return maintenanceCount;
    }
    
    public void setMaintenanceCount(int maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }
}

