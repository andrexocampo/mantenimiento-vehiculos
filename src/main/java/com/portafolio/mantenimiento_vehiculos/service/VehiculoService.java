package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.InterfaceVehiculo;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Vehicle operations
 * @author Andres
 */
@Service
public class VehiculoService implements InterfaceVehiculoService {
    @Autowired
    private InterfaceVehiculo data;
    
    @Override
    public List<Vehiculo> listar(){
        return (List<Vehiculo>)data.findAll();
    }
    
    @Override
    public Optional<Vehiculo> listarId(int id){
        return data.findById(id);
    }
    
    @Override
    public int save(Vehiculo v){
        int res=0;
        Vehiculo vehicle=data.save(v);
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
    public List<Mantenimiento> listarMantenimientos(int id){
        return data.findMantenimientosByVehiculoId(id);
    }
}
