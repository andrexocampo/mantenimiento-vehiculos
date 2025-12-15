package com.portafolio.mantenimiento_vehiculos.interfacesService;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Vehicle operations
 * @author Andres
 */
public interface VehicleServiceInterface {
    public List<Vehicle> listar();
    public Optional<Vehicle> listarId(int id);
    public void delete(int id);
    public int save(Vehicle v);
    public List<Maintenance> listarMantenimientos(int id);
}


