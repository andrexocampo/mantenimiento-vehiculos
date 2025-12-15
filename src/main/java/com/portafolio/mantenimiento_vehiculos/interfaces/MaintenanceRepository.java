package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Maintenance entity
 * @author Andres
 */
@Repository
public interface MaintenanceRepository extends CrudRepository<Maintenance,Integer> {
    
    /**
     * Get all maintenances for vehicles owned by a specific user
     */
    @Query("SELECT m FROM Maintenance m WHERE m.vehicle.user = :user")
    List<Maintenance> findByUser(@Param("user") User user);
    
    /**
     * Get pending maintenances (not paid) for vehicles owned by a specific user
     * Ordered by expiration date (oldest first - most urgent first)
     */
    @Query("SELECT m FROM Maintenance m WHERE m.vehicle.user = :user AND m.paid = false ORDER BY m.expirationDate ASC NULLS LAST")
    List<Maintenance> findPendingByUser(@Param("user") User user);
    
    /**
     * Get completed maintenances (paid) for vehicles owned by a specific user
     * Ordered by payment date (most recent first)
     */
    @Query("SELECT m FROM Maintenance m WHERE m.vehicle.user = :user AND m.paid = true ORDER BY m.paymentDate DESC NULLS LAST")
    List<Maintenance> findCompletedByUser(@Param("user") User user);
}

