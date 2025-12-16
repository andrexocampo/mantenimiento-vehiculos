package com.portafolio.mantenimiento_vehiculos.model.dto;

/**
 * DTO for vehicle cost data used in bar chart
 * @author Andres
 */
public class VehicleCostDTO {
    private String vehicleName;
    private String licensePlate;
    private float totalCost;
    
    public VehicleCostDTO() {}
    
    public VehicleCostDTO(String vehicleName, String licensePlate, float totalCost) {
        this.vehicleName = vehicleName;
        this.licensePlate = licensePlate;
        this.totalCost = totalCost;
    }
    
    // Getters and Setters
    public String getVehicleName() {
        return vehicleName;
    }
    
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public float getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}

