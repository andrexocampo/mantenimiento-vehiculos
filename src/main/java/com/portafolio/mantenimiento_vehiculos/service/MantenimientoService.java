package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.InterfazMantenimiento;
import com.portafolio.mantenimiento_vehiculos.interfacesService.InterfazMantenimientoService;
import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Maintenance operations
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
    
    @Override
    public int save(Mantenimiento m){
        int res=0;
        Mantenimiento maintenance=data.save(m);
        if(!maintenance.equals(null)){
            res=1;
        }
        return res;
    }
    
    @Override
    public void delete(int id){
        data.deleteById(id);
    }
}
