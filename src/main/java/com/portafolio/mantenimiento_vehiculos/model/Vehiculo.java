/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Andres
 */
@Entity
@Table(name="Vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String placa;
    private String descripcion;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="fk_vehiculo_id",referencedColumnName="id")
    private List<Mantenimiento> mantenimientos;
    
    //private int tiempoCambioAceite;
    
    public Vehiculo(){};
    
    public void setid(int id){
        this.id=id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getNombre(){
        return nombre;
    }
    
    public void setPlaca(String placa){
        this.placa=placa;
    }
    public String getPlaca(){
        return placa;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    public String getDescripcion(){
        return descripcion;
    }  
    
    /*
    public void setMantenimientos(List<Mantenimiento> mantenimientos){
        this.mantenimientos=mantenimientos;
    }
    
    public List<Mantenimiento> getMantenimientos(){
        return mantenimientos;
    }
    */
}