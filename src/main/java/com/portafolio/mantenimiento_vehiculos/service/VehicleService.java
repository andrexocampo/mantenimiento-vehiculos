package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.VehicleRepository;
import com.portafolio.mantenimiento_vehiculos.interfacesService.VehicleServiceInterface;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Vehicle operations
 * @author Andres
 */
@Service
public class VehicleService implements VehicleServiceInterface {
    @Autowired
    private VehicleRepository data;
    
    @Override
    public List<Vehicle> listar(){
        return (List<Vehicle>)data.findAll();
    }
    
    @Override
    public Optional<Vehicle> listarId(int id){
        return data.findById(id);
    }
    
    @Override
    public int save(Vehicle v){
        int res=0;
        Vehicle vehicle=data.save(v);
        if(!vehicle.equals(null)){
            res=1;
        }
        return res;
    }
    
    @Override
    public void delete(int id){
        data.deleteById(id);
    }
    
    @Override
    public List<Maintenance> listarMantenimientos(int id){
        return data.findMantenimientosByVehiculoId(id);
    }
}

