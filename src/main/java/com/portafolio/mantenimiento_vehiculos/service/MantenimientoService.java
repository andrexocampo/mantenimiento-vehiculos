/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.InterfazMantenimiento;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfazMantenimientoService;
import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Andres
 */
@Service
public class MantenimientoService implements InterfazMantenimientoService{
    @Autowired
    private InterfazMantenimiento data;
    
    @Override
    public List<Mantenimiento> listar(){
        return (List<Mantenimiento>)data.findAll();
    }
    
    @Override
    public Optional<Mantenimiento> listarId(int id){
        return data.findById(id);
    }
    
    /*public List<Mantenimiento> listarMantenimientosVehiculo(int idVehiculo){
        List<Mantenimiento> mantenimientosGenerales=listar();
        List<Mantenimiento> mantenimientosVehiculo=new ArrayList<>();
        //Recorrer el arreglo y llenarlo con los mantenimientos asociados con el vehiculo
        for(int i =0;i<mantenimientosGenerales.size();i++){
            if(mantenimientosGenerales.get(i).getVehiculo().getId()==idVehiculo){
                mantenimientosVehiculo.add(mantenimientosGenerales.get(i));
            }
        }
        
        return mantenimientosVehiculo;
    }*/
    
    @Override
    public int save(Mantenimiento m){
        int res=0;
        Mantenimiento mantenimiento=data.save(m);
        if(!mantenimiento.equals(null)){
            res=1;
        }
        return res;
    }
    
    @Override
    public void delete(int id){
        data.deleteById(id);
    }
}
