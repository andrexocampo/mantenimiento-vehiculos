package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Maintenance entity
 * @author Andres
 */
@Repository
public interface MaintenanceRepository extends CrudRepository<Maintenance,Integer> {
    
}

