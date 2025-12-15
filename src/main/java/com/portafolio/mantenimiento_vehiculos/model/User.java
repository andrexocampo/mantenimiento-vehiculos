package com.portafolio.mantenimiento_vehiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * User entity representing a user in the system
 * @author Andres
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private boolean enabled = true;
    private String role = "USER";
    
    private String email;
    private boolean emailNotificationsEnabled = false;
    private boolean notifyOneMonthBefore = false;
    private boolean notifyOneWeekBefore = true;
    private boolean notifyOneDayBefore = true;
    
    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;
    
    // Constructors
    public User() {}
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }
    
    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }
    
    public boolean isNotifyOneWeekBefore() {
        return notifyOneWeekBefore;
    }
    
    public void setNotifyOneWeekBefore(boolean notifyOneWeekBefore) {
        this.notifyOneWeekBefore = notifyOneWeekBefore;
    }
    
    public boolean isNotifyOneDayBefore() {
        return notifyOneDayBefore;
    }
    
    public void setNotifyOneDayBefore(boolean notifyOneDayBefore) {
        this.notifyOneDayBefore = notifyOneDayBefore;
    }
    
    public boolean isNotifyOneMonthBefore() {
        return notifyOneMonthBefore;
    }
    
    public void setNotifyOneMonthBefore(boolean notifyOneMonthBefore) {
        this.notifyOneMonthBefore = notifyOneMonthBefore;
    }
}

