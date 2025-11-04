/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.interfaces;


import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Andres
 */
@Repository
public interface InterfazMantenimiento extends CrudRepository<Mantenimiento,Integer> {
    
}
