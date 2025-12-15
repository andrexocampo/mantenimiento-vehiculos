package com.portafolio.mantenimiento_vehiculos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Maintenance entity representing a maintenance record for a vehicle
 * @author Andres
 */
@Entity
@Table(name="mantenimiento")
public class Mantenimiento {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mant_id")
    private int id;
    
    @Column(name="descripcion")
    private String description;
    
    @Column(name="pagado")
    private boolean paid;
    
    @Column(name="fecha_de_pago")
    private LocalDate paymentDate;
    
    @Column(name="fecha_de_caducidad")
    private LocalDate expirationDate;
    
    @Column(name="costo")
    private float cost;
    
    @ManyToOne
    @JoinColumn(name="fk_vehiculo_id")
    private Vehiculo vehicle;
    
    public Mantenimiento(){}
    
    public void setId(int id){
        this.id=id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setPaid(boolean paid){
        this.paid=paid;
    }
    
    public boolean isPaid(){
        return paid;
    }
    
    public void setPaymentDate(LocalDate paymentDate){
        this.paymentDate=paymentDate;
    }
    
    public LocalDate getPaymentDate(){
        return paymentDate;
    }
    
    public void setExpirationDate(LocalDate expirationDate){
        this.expirationDate=expirationDate;
    }
    
    public LocalDate getExpirationDate(){
        return expirationDate;
    }
    
    public void setCost(float cost){
        this.cost=cost;
    }
    
    public float getCost(){
        return cost;
    }
    
    public void setVehicle(Vehiculo vehicle){
        this.vehicle=vehicle;
    }
    
    public Vehiculo getVehicle(){
        return vehicle;
    }
}
