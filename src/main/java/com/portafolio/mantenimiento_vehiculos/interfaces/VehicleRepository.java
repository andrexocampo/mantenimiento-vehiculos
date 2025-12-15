package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Vehicle entity
 * @author Andres
 */
@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {  
    
    @Query("SELECT v.maintenances FROM Vehicle v WHERE v.id = :id")
    List<Maintenance> findMantenimientosByVehiculoId(@Param("id") int id);
    
    List<Vehicle> findByUser(User user);
    
    @Query("SELECT v FROM Vehicle v WHERE v.user.id = :userId")
    List<Vehicle> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT v FROM Vehicle v WHERE v.user IS NULL")
    List<Vehicle> findVehiclesWithoutUser();
}

