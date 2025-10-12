/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portafolio.mantenimiento_vehiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

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
    private LocalDate pagoSoat;
    private LocalDate pagoTecno;
    private LocalDate cambioAceite;
    private int tiempoCambioAceite;
    
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
    
    public void setPagoSoat(LocalDate pagoSoat){
        this.pagoSoat=pagoSoat;
    }
    public LocalDate getPagoSoat(){
        return pagoSoat;
    }
    
    public void setPagoTecno(LocalDate pagoTecno){
        this.pagoTecno=pagoTecno;
    }
    public LocalDate getPagoTecno(){
        return pagoTecno;
    }
    
    
    public void setCambioAceite(LocalDate cambioAceite){
        this.cambioAceite=cambioAceite;
    }
    public LocalDate getCambioAceite(){
        return cambioAceite;
    }
    
    
}
