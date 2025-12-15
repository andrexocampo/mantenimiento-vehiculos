package com.portafolio.mantenimiento_vehiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class
 * 
 * TO ENABLE SCHEDULED NOTIFICATIONS (Option 2):
 * Add @EnableScheduling annotation below:
 * 
 * @SpringBootApplication
 * @EnableScheduling  // <-- Uncomment this line
 * public class MantenimientoVehiculosApplication { ... }
 * 
 * Then uncomment the @Scheduled method in ScheduledNotificationService
 * 
 * @author Andres
 */
@SpringBootApplication
// @EnableScheduling  // Uncomment to enable scheduled tasks (Option 2)
public class MantenimientoVehiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MantenimientoVehiculosApplication.class, args);
	}

}
