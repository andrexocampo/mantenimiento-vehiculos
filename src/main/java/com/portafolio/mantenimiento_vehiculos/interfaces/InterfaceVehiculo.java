/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Andres
 */
@Repository
public interface InterfaceVehiculo extends CrudRepository<Vehiculo,Integer> {  
    
    @Query("SELECT v.mantenimientos FROM Vehiculo v WHERE v.id = :id")
    List<Mantenimiento> findMantenimientosByVehiculoId(@Param("id") int id);
}
