package com.portafolio.mantenimiento_vehiculos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for scheduled notification tasks (OPTION 2)
 * 
 * THIS SERVICE IS PREPARED BUT NOT ACTIVATED
 * 
 * TO ACTIVATE SCHEDULED NOTIFICATIONS:
 * 1. Add EnableScheduling to MantenimientoVehiculosApplication
 * 2. Uncomment the Scheduled annotation and imports below
 * 3. Configure email in application.properties (see EmailService.java)
 * 4. Uncomment the method implementation
 * 5. Test in a production environment
 * 
 * Cron expression examples:
 * "0 0 9 * * ?" = Every day at 9:00 AM
 * "0 0 9 * * MON-FRI" = Every weekday at 9:00 AM
 * "0 0 0/6 * * ?" = Every 6 hours
 */
@Service
public class ScheduledNotificationService {
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * Scheduled task to check all users and send email notifications
     * 
     * CURRENTLY DISABLED - Uncomment to activate
     * 
     * TO ACTIVATE:
     * 1. Uncomment: import org.springframework.scheduling.annotation.Scheduled;
     * 2. Uncomment: import com.portafolio.mantenimiento_vehiculos.interfaces.UserRepository;
     * 3. Uncomment: import com.portafolio.mantenimiento_vehiculos.model.User;
     * 4. Uncomment: import java.util.List;
     * 5. Uncomment the @Scheduled annotation below
     * 6. Uncomment the method body
     */
    // @Scheduled(cron = "0 0 9 * * ?") // Every day at 9:00 AM
    public void checkAllUsersAndSendNotifications() {
        // Implementation prepared for Option 2
        // See method documentation above for activation instructions
    }
}

