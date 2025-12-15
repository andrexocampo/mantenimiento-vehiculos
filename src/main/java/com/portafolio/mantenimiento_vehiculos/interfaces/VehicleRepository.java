package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
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
}

