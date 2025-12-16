package com.portafolio.mantenimiento_vehiculos.model.dto;

/**
 * DTO for monthly expense data used in timeline chart
 * @author Andres
 */
public class MonthlyExpenseDTO {
    private String month; // "2024-01"
    private String monthLabel; // "January 2024"
    private float totalExpense;
    
    public MonthlyExpenseDTO() {}
    
    public MonthlyExpenseDTO(String month, String monthLabel, float totalExpense) {
        this.month = month;
        this.monthLabel = monthLabel;
        this.totalExpense = totalExpense;
    }
    
    // Getters and Setters
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public String getMonthLabel() {
        return monthLabel;
    }
    
    public void setMonthLabel(String monthLabel) {
        this.monthLabel = monthLabel;
    }
    
    public float getTotalExpense() {
        return totalExpense;
    }
    
    public void setTotalExpense(float totalExpense) {
        this.totalExpense = totalExpense;
    }
}

