package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import java.time.LocalDate;
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
    
    /**
     * Get total costs of all paid maintenances for a user
     */
    @Query("SELECT COALESCE(SUM(m.cost), 0) FROM Maintenance m WHERE m.vehicle.user = :user AND m.paid = true")
    Float getTotalCostsByUser(@Param("user") User user);
    
    /**
     * Get total costs of paid maintenances for current year
     */
    @Query("SELECT COALESCE(SUM(m.cost), 0) FROM Maintenance m WHERE m.vehicle.user = :user " +
           "AND m.paid = true AND YEAR(m.paymentDate) = YEAR(CURRENT_DATE)")
    Float getTotalCostsThisYearByUser(@Param("user") User user);
    
    /**
     * Get total costs of paid maintenances in the last 12 months
     */
    @Query("SELECT COALESCE(SUM(m.cost), 0) FROM Maintenance m WHERE m.vehicle.user = :user " +
           "AND m.paid = true AND m.paymentDate >= :startDate")
    Float getTotalCostsLast12MonthsByUser(@Param("user") User user, @Param("startDate") LocalDate startDate);
    
    /**
     * Get count of completed maintenances in the last 12 months
     */
    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.vehicle.user = :user " +
           "AND m.paid = true AND m.paymentDate >= :startDate")
    Long getCompletedMaintenancesLast12MonthsByUser(@Param("user") User user, @Param("startDate") LocalDate startDate);
    
    /**
     * Get total costs of pending maintenances expiring this month for a user
     */
    @Query("SELECT COALESCE(SUM(m.cost), 0) FROM Maintenance m WHERE m.vehicle.user = :user " +
           "AND m.paid = false AND MONTH(m.expirationDate) = MONTH(CURRENT_DATE) " +
           "AND YEAR(m.expirationDate) = YEAR(CURRENT_DATE)")
    Float getPendingCostsThisMonthByUser(@Param("user") User user);
    
    /**
     * Get total costs of pending maintenances expiring in the next 12 months for a user
     */
    @Query("SELECT COALESCE(SUM(m.cost), 0) FROM Maintenance m WHERE m.vehicle.user = :user " +
           "AND m.paid = false AND m.expirationDate >= CURRENT_DATE " +
           "AND m.expirationDate <= :endDate")
    Float getPendingCostsNext12MonthsByUser(@Param("user") User user, @Param("endDate") LocalDate endDate);
    
    /**
     * Get total cost per vehicle (only paid maintenances) for a user
     * Returns list of Object[] where [0] = Vehicle, [1] = totalCost (Double)
     */
    @Query("SELECT m.vehicle, SUM(m.cost) FROM Maintenance m " +
           "WHERE m.vehicle.user = :user AND m.paid = true " +
           "GROUP BY m.vehicle.id")
    List<Object[]> getVehicleCostsByUser(@Param("user") User user);
    
    /**
     * Get total cost per vehicle (only paid maintenances) in the last 12 months
     * Returns list of Object[] where [0] = Vehicle, [1] = totalCost (Double)
     */
    @Query("SELECT m.vehicle, SUM(m.cost) FROM Maintenance m " +
           "WHERE m.vehicle.user = :user AND m.paid = true AND m.paymentDate >= :startDate " +
           "GROUP BY m.vehicle.id")
    List<Object[]> getVehicleCostsLast12MonthsByUser(@Param("user") User user, @Param("startDate") LocalDate startDate);
    
    /**
     * Get monthly expenses for paid maintenances in the last 12 months
     * Returns list of Object[] where [0] = year-month string, [1] = totalCost (Double)
     */
    @Query(value = "SELECT DATE_FORMAT(m.fecha_de_pago, '%Y-%m') as month, " +
           "COALESCE(SUM(m.costo), 0) as total FROM mantenimiento m " +
           "INNER JOIN Vehiculos v ON m.fk_vehiculo_id = v.id " +
           "WHERE v.user_id = :userId AND m.pagado = true " +
           "AND m.fecha_de_pago >= :startDate " +
           "GROUP BY DATE_FORMAT(m.fecha_de_pago, '%Y-%m') " +
           "ORDER BY month ASC", nativeQuery = true)
    List<Object[]> getMonthlyExpensesByUser(@Param("userId") Long userId, 
                                             @Param("startDate") LocalDate startDate);
    
    /**
     * Get quarterly summary for current quarter
     * Returns list of Object[] where [0] = totalCost (Double), [1] = count (Long)
     */
    @Query(value = "SELECT COALESCE(SUM(m.costo), 0) as total, COUNT(*) as count " +
           "FROM mantenimiento m " +
           "INNER JOIN Vehiculos v ON m.fk_vehiculo_id = v.id " +
           "WHERE v.user_id = :userId AND m.pagado = true " +
           "AND YEAR(m.fecha_de_pago) = YEAR(CURRENT_DATE) " +
           "AND QUARTER(m.fecha_de_pago) = QUARTER(CURRENT_DATE)", nativeQuery = true)
    List<Object[]> getCurrentQuarterSummaryByUser(@Param("userId") Long userId);
}

