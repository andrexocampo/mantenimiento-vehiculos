package com.portafolio.mantenimiento_vehiculos.interfacesService;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Maintenance operations
 * @author Andres
 */
public interface MaintenanceServiceInterface {
    public List<Maintenance> listar();
    public Optional<Maintenance> listarId(int id);
    public void delete(int id);
    public int save(Maintenance m);
    
    /**
     * Get all maintenances for the current authenticated user
     */
    public List<Maintenance> listAllByUser();
    
    /**
     * Get pending maintenances (not paid) for the current authenticated user
     */
    public List<Maintenance> listPendingMaintenances();
    
    /**
     * Get completed maintenances (paid) for the current authenticated user
     */
    public List<Maintenance> listCompletedMaintenances();
    
    /**
     * Mark a maintenance as paid
     * @param id Maintenance ID
     * @param actualCost Actual cost paid (can be null to keep original cost)
     */
    public void markAsPaid(int id, Float actualCost);
    
    /**
     * Revert payment status of a maintenance
     */
    public void revertPayment(int id);
}

