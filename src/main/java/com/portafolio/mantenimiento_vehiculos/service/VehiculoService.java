/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.InterfaceVehiculo;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfaceVehiculoService;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int save(Vehiculo v){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void delete(int id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
