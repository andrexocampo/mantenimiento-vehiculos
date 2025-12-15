package com.portafolio.mantenimiento_vehiculos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 * Vehicle entity representing a vehicle in the maintenance system
 * @author Andres
 */
@Entity
@Table(name="Vehiculos")
public class Vehicle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(name="nombre")
    private String name;
    
    @Column(name="placa")
    private String licensePlate;
    
    @Column(name="descripcion")
    private String description;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="fk_vehiculo_id",referencedColumnName="id")
    private List<Maintenance> maintenances;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    public Vehicle(){}
    
    public void setId(int id){
        this.id=id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setLicensePlate(String licensePlate){
        this.licensePlate=licensePlate;
    }
    
    public String getLicensePlate(){
        return licensePlate;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setMaintenances(List<Maintenance> maintenances){
        this.maintenances=maintenances;
    }
    
    public List<Maintenance> getMaintenances(){
        return maintenances;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
