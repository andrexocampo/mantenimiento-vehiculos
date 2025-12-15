package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.InterfaceVehiculo;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.User;
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
    
    @Autowired
    private SecurityService securityService;
    
    @Override
    public List<Vehiculo> listar(){
        User currentUser = securityService.getCurrentUser();
        return data.findByUser(currentUser);
    }
    
    @Override
    public Optional<Vehiculo> listarId(int id){
        Optional<Vehiculo> vehicle = data.findById(id);
        if (vehicle.isPresent()) {
            User vehicleUser = vehicle.get().getUser();
            // If vehicle has no user, return empty
            if (vehicleUser == null) {
                return Optional.empty();
            }
            User currentUser = securityService.getCurrentUser();
            // Verify that the vehicle belongs to the current user
            if (vehicleUser.getId() != currentUser.getId()) {
                return Optional.empty();
            }
        }
        return vehicle;
    }
    
    @Override
    public int save(Vehiculo v){
        int res=0;
        // If vehicle doesn't have a user, assign current user
        if (v.getUser() == null) {
            User currentUser = securityService.getCurrentUser();
            v.setUser(currentUser);
        }
        Vehiculo vehicle=data.save(v);
        if(!vehicle.equals(null)){
            res=1;
        }
        return res;
    }
    
    @Override
    public void delete(int id){
        Optional<Vehiculo> vehicle = data.findById(id);
        if (vehicle.isPresent()) {
            User vehicleUser = vehicle.get().getUser();
            // If vehicle has no user, don't allow deletion
            if (vehicleUser == null) {
                return;
            }
            User currentUser = securityService.getCurrentUser();
            // Verify that the vehicle belongs to the current user
            if (vehicleUser.getId() == currentUser.getId()) {
                data.deleteById(id);
            }
        }
    }
    
    @Override
    public List<Mantenimiento> listarMantenimientos(int id){
        Optional<Vehiculo> vehicle = data.findById(id);
        if (vehicle.isPresent()) {
            User vehicleUser = vehicle.get().getUser();
            // If vehicle has no user, return empty list
            if (vehicleUser == null) {
                return List.of();
            }
            User currentUser = securityService.getCurrentUser();
            // Verify that the vehicle belongs to the current user
            if (vehicleUser.getId() == currentUser.getId()) {
                return data.findMantenimientosByVehiculoId(id);
            }
        }
        return List.of();
    }
}
