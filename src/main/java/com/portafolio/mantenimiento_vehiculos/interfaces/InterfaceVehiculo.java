package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
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
public interface InterfaceVehiculo extends CrudRepository<Vehiculo,Integer> {  
    
    @Query("SELECT v.maintenances FROM Vehiculo v WHERE v.id = :id")
    List<Mantenimiento> findMantenimientosByVehiculoId(@Param("id") int id);
}
