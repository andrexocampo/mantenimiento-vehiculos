package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.MaintenanceRepository;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.model.dto.NotificationResult;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling maintenance notifications
 * Currently implements Option 1: Check on login
 * Prepared for Option 2: Scheduled tasks (see checkAllUsersAndSendNotifications method)
 * @author Andres
 */
@Service
public class NotificationService {
    
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    
    @Autowired
    private EmailService emailService;
    
    /**
     * OPCIÓN 1: Check maintenances for a specific user
     * Called when user logs in to show notifications
     * 
     * @param user The user to check maintenances for
     * @return NotificationResult with pending maintenances information
     */
    public NotificationResult checkMaintenances(User user) {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthFromNow = today.plusMonths(1);
        LocalDate oneWeekFromNow = today.plusWeeks(1);
        LocalDate oneDayFromNow = today.plusDays(1);
        
        // Get all pending maintenances for the user
        List<Maintenance> pendingMaintenances = maintenanceRepository.findPendingByUser(user);
        
        NotificationResult result = new NotificationResult();
        
        // Count expired maintenances (expiration date has passed)
        long expiredCount = pendingMaintenances.stream()
            .filter(m -> m.getExpirationDate() != null && m.getExpirationDate().isBefore(today))
            .count();
        result.setExpiredCount(expiredCount);
        
        // Get maintenances expiring in 1 month (but not in 1 week)
        if (user.isNotifyOneMonthBefore()) {
            List<Maintenance> oneMonthBefore = pendingMaintenances.stream()
                .filter(m -> m.getExpirationDate() != null 
                    && m.getExpirationDate().isAfter(today)
                    && m.getExpirationDate().isBefore(oneMonthFromNow)
                    && m.getExpirationDate().isAfter(oneWeekFromNow))
                .collect(Collectors.toList());
            result.setOneMonthBefore(oneMonthBefore);
        }
        
        // Get maintenances expiring in 1 week (but not in 1 day)
        if (user.isNotifyOneWeekBefore()) {
            List<Maintenance> oneWeekBefore = pendingMaintenances.stream()
                .filter(m -> m.getExpirationDate() != null 
                    && m.getExpirationDate().isAfter(today)
                    && m.getExpirationDate().isBefore(oneWeekFromNow)
                    && m.getExpirationDate().isAfter(oneDayFromNow))
                .collect(Collectors.toList());
            result.setOneWeekBefore(oneWeekBefore);
        }
        
        // Get maintenances expiring in 1 day
        if (user.isNotifyOneDayBefore()) {
            List<Maintenance> oneDayBefore = pendingMaintenances.stream()
                .filter(m -> m.getExpirationDate() != null 
                    && m.getExpirationDate().isAfter(today)
                    && m.getExpirationDate().isBefore(oneDayFromNow))
                .collect(Collectors.toList());
            result.setOneDayBefore(oneDayBefore);
        }
        
        // Send email notification if enabled
        if (user.isEmailNotificationsEnabled() && result.hasNotifications() && user.getEmail() != null && !user.getEmail().isEmpty()) {
            emailService.sendNotificationEmail(user, result);
        }
        
        return result;
    }
    
    /**
     * OPCIÓN 2: Check all users and send notifications (PREPARED FOR SCHEDULED TASKS)
     * 
     * This method is prepared to be used with @Scheduled annotation for automatic daily checks.
     * 
     * TO ACTIVATE OPTION 2:
     * 1. Uncomment the @Scheduled annotation in ScheduledNotificationService
     * 2. Add @EnableScheduling to MantenimientoVehiculosApplication
     * 3. Configure email properties in application.properties
     * 4. Uncomment and implement the logic below
     * 
     * Example cron expression: "0 0 9 * * ?" = Every day at 9:00 AM
     */
    /*
    public void checkAllUsersAndSendNotifications() {
        // TODO: Uncomment when implementing Option 2
        // List<User> users = userRepository.findAll();
        // for (User user : users) {
        //     if (user.isEmailNotificationsEnabled() && user.getEmail() != null && !user.getEmail().isEmpty()) {
        //         NotificationResult result = checkMaintenances(user);
        //         if (result.hasNotifications()) {
        //             // Email is already sent in checkMaintenances method
        //             // This method just triggers the check for all users
        //         }
        //     }
        // }
    }
    */
}

