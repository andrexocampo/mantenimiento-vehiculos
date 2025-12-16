package com.portafolio.mantenimiento_vehiculos.config;

import com.portafolio.mantenimiento_vehiculos.interfaces.MaintenanceRepository;
import com.portafolio.mantenimiento_vehiculos.interfaces.UserRepository;
import com.portafolio.mantenimiento_vehiculos.interfaces.VehicleRepository;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Test data initializer that creates a test user with vehicles and maintenances
 * @author Andres
 */
@Component
@Order(2) // Run after DataInitializer
public class TestDataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private final Random random = new Random();
    
    @Override
    public void run(String... args) throws Exception {
        // Create test user if it doesn't exist
        User testUser;
        if (!userRepository.existsByUsername("testuser")) {
            testUser = new User();
            testUser.setUsername("testuser");
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setEnabled(true);
            testUser.setRole("USER");
            testUser.setEmail("testuser@example.com");
            testUser.setEmailNotificationsEnabled(true);
            testUser.setNotifyOneMonthBefore(true);
            testUser.setNotifyOneWeekBefore(true);
            testUser.setNotifyOneDayBefore(true);
            testUser = userRepository.save(testUser);
            System.out.println("✓ Test user created: testuser/test123");
        } else {
            testUser = userRepository.findByUsername("testuser").get();
            System.out.println("✓ Test user already exists");
            
            // Check if test data already exists
            List<Vehicle> existingVehicles = vehicleRepository.findByUser(testUser);
            if (!existingVehicles.isEmpty()) {
                System.out.println("✓ Test data already exists (" + existingVehicles.size() + " vehicles)");
                return;
            }
        }
        
        // Create 4 vehicles
        String[] vehicleNames = {
            "Toyota Corolla 2020",
            "Honda Civic 2019",
            "Ford F-150 2021",
            "Chevrolet Silverado 2022"
        };
        
        String[] licensePlates = {
            "ABC-123",
            "XYZ-789",
            "DEF-456",
            "GHI-012"
        };
        
        String[] descriptions = {
            "Compact sedan, white color",
            "Sport sedan, black color",
            "Pickup truck, red color",
            "Pickup truck, blue color"
        };
        
        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setName(vehicleNames[i]);
            vehicle.setLicensePlate(licensePlates[i]);
            vehicle.setDescription(descriptions[i]);
            vehicle.setUser(testUser);
            vehicle = vehicleRepository.save(vehicle);
            vehicles.add(vehicle);
            System.out.println("✓ Created vehicle: " + vehicle.getLicensePlate());
        }
        
        // Create maintenances for each vehicle
        LocalDate today = LocalDate.now();
        LocalDate twelveMonthsAgo = today.minusMonths(12);
        LocalDate twelveMonthsAhead = today.plusMonths(12);
        
        String[] maintenanceTypes = {
            "Oil change",
            "Brake inspection",
            "Wheel alignment and balancing",
            "Tire replacement",
            "Engine inspection",
            "Filter replacement",
            "Suspension check",
            "Preventive maintenance",
            "Transmission repair",
            "Electrical system check"
        };
        
        int totalMaintenances = 0;
        for (Vehicle vehicle : vehicles) {
            // Create 10 maintenances per vehicle
            for (int i = 0; i < 10; i++) {
                Maintenance maintenance = new Maintenance();
                
                // Random maintenance type
                maintenance.setDescription(maintenanceTypes[random.nextInt(maintenanceTypes.length)]);
                
                // Random cost between 5000 and 2000000
                float cost = 5000 + random.nextFloat() * (2000000 - 5000);
                maintenance.setCost(cost);
                
                // Random date within 12 months back and 12 months ahead (24 months range)
                int daysRange = (int) java.time.temporal.ChronoUnit.DAYS.between(twelveMonthsAgo, twelveMonthsAhead);
                int randomDays = random.nextInt(daysRange);
                LocalDate randomDate = twelveMonthsAgo.plusDays(randomDays);
                
                // 60% chance of being paid, 40% pending
                boolean isPaid = random.nextFloat() < 0.6f;
                maintenance.setPaid(isPaid);
                
                if (isPaid) {
                    // If paid, payment date is before or on the expiration date
                    maintenance.setPaymentDate(randomDate);
                    // Expiration date is 3-12 months after payment
                    int expirationMonths = 3 + random.nextInt(10);
                    maintenance.setExpirationDate(randomDate.plusMonths(expirationMonths));
                } else {
                    // If not paid, expiration date is in the future
                    if (randomDate.isBefore(today)) {
                        // If random date is in the past, set expiration to future
                        int futureMonths = 1 + random.nextInt(12);
                        maintenance.setExpirationDate(today.plusMonths(futureMonths));
                    } else {
                        // If random date is in the future, use it as expiration
                        int expirationMonths = 1 + random.nextInt(6);
                        maintenance.setExpirationDate(randomDate.plusMonths(expirationMonths));
                    }
                    maintenance.setPaymentDate(null);
                }
                
                maintenance.setVehicle(vehicle);
                maintenanceRepository.save(maintenance);
                totalMaintenances++;
            }
            System.out.println("✓ Created 10 maintenances for vehicle: " + vehicle.getLicensePlate());
        }
        
        System.out.println("✓ Test data initialization complete!");
        System.out.println("  - User: testuser/test123");
        System.out.println("  - Vehicles: " + vehicles.size());
        System.out.println("  - Total maintenances: " + totalMaintenances);
    }
}

