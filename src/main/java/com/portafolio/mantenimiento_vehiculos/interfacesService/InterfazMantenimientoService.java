/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.interfacesService;

import com.portafolio.mantenimiento_vehiculos.model.Mantenimiento;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Andres
 */
public interface InterfazMantenimientoService {
    public List<Mantenimiento> listar();
    public Optional<Mantenimiento> listarId(int id);
    public void delete(int id);
    public int save(Mantenimiento m);
    //public List<Mantenimiento> listarMantenimientosVehiculo(int idVehiculo);
}
