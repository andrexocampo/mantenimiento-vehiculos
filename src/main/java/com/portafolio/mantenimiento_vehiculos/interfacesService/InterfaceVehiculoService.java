/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.interfacesService;

import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Andres
 */
public interface InterfaceVehiculoService {
    public List<Vehiculo> listar();
    public Optional<Vehiculo> listarId(int id);
    public void delete(int id);
    public int save(Vehiculo v);
}
