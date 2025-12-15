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
}

