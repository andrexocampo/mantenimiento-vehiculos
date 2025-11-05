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
@Table(name="mantenimiento")
public class Mantenimiento {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int mant_id;
    private String descripcion;
    private boolean pagado;
    private LocalDate fecha_de_pago;
    private LocalDate fecha_de_caducidad;
    private float costo;
    
    
    public Mantenimiento(){}
    
    public void setMant_id(int mant_id){
        this.mant_id=mant_id;
    }
    
    public int getMant_id(){
        return mant_id;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    public String getDescripcion(){
        return descripcion;
    }  
    
    public void setPagado(boolean pagado){
        this.pagado=pagado;
    }
    
    public boolean getPagado(){
        return pagado;
    }
    
    public void setFecha_de_pago(LocalDate fecha_de_pago){
        this.fecha_de_pago=fecha_de_pago;
    }
    public LocalDate getFecha_de_pago(){
        return fecha_de_pago;
    }
    
    public void setFecha_de_caducidad(LocalDate fecha_de_caducidad){
        this.fecha_de_caducidad=fecha_de_caducidad;
    }
    public LocalDate getFecha_de_caducidad(){
        return fecha_de_caducidad;
    }
    
    
    public void setCosto(float costo){
        this.costo=costo;
    }
    public float getCosto(){
        return costo;
    }
    
}
