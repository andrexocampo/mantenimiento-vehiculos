package com.portafolio.mantenimiento_vehiculos.config;

import com.portafolio.mantenimiento_vehiculos.interfaces.InterfaceVehiculo;
import com.portafolio.mantenimiento_vehiculos.interfaces.UserRepository;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data initializer that creates default admin user and assigns existing vehicles
 * @author Andres
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InterfaceVehiculo vehicleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        User admin;
        if (!userRepository.existsByUsername("admin")) {
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRole("USER");
            admin = userRepository.save(admin);
            System.out.println("✓ Default admin user created: admin/admin123");
        } else {
            admin = userRepository.findByUsername("admin").get();
            System.out.println("✓ Admin user already exists");
        }
        
        // Assign all vehicles without user to the default admin user
        List<Vehiculo> vehiclesWithoutUser = vehicleRepository.findVehiclesWithoutUser();
        if (!vehiclesWithoutUser.isEmpty()) {
            for (Vehiculo vehicle : vehiclesWithoutUser) {
                vehicle.setUser(admin);
                vehicleRepository.save(vehicle);
            }
            System.out.println("✓ Assigned " + vehiclesWithoutUser.size() + " existing vehicle(s) to admin user");
        } else {
            System.out.println("✓ No orphaned vehicles found");
        }
    }
}

