package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import com.portafolio.mantenimiento_vehiculos.model.dto.NotificationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending email notifications
 * 
 * TO CONFIGURE EMAIL (for production):
 * Add these properties to application.properties:
 * 
 * spring.mail.host=smtp.gmail.com
 * spring.mail.port=587
 * spring.mail.username=your-email@gmail.com
 * spring.mail.password=your-app-password
 * spring.mail.properties.mail.smtp.auth=true
 * spring.mail.properties.mail.smtp.starttls.enable=true
 * 
 * @author Andres
 */
@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    /**
     * Send notification email to user about pending maintenances
     * 
     * @param user The user to send email to
     * @param result Notification result with maintenance information
     */
    public void sendNotificationEmail(User user, NotificationResult result) {
        if (mailSender == null) {
            logger.warn("JavaMailSender is not configured. Email notification skipped for user: {}", user.getUsername());
            return;
        }
        
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            logger.warn("User {} does not have an email address configured", user.getUsername());
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Maintenance Reminder - Vehicle Maintenance System");
            message.setText(buildEmailContent(user, result));
            
            mailSender.send(message);
            logger.info("Notification email sent successfully to: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Error sending notification email to {}: {}", user.getEmail(), e.getMessage());
        }
    }
    
    /**
     * Build email content with maintenance information
     */
    private String buildEmailContent(User user, NotificationResult result) {
        StringBuilder content = new StringBuilder();
        content.append("Hello ").append(user.getUsername()).append(",\n\n");
        content.append("This is a reminder about your vehicle maintenances:\n\n");
        
        if (result.getExpiredCount() > 0) {
            content.append("⚠️ EXPIRED: You have ").append(result.getExpiredCount())
                   .append(" expired maintenance(s) that need immediate attention!\n\n");
        }
        
        if (!result.getOneDayBefore().isEmpty()) {
            content.append("🔴 URGENT: ").append(result.getOneDayBefore().size())
                   .append(" maintenance(s) expiring in 1 day:\n");
            for (Maintenance m : result.getOneDayBefore()) {
                content.append("  - ").append(m.getDescription())
                       .append(" (Vehicle: ").append(m.getVehicle().getLicensePlate())
                       .append(") - Expires: ").append(m.getExpirationDate()).append("\n");
            }
            content.append("\n");
        }
        
        if (!result.getOneWeekBefore().isEmpty()) {
            content.append("🟡 REMINDER: ").append(result.getOneWeekBefore().size())
                   .append(" maintenance(s) expiring in 1 week:\n");
            for (Maintenance m : result.getOneWeekBefore()) {
                content.append("  - ").append(m.getDescription())
                       .append(" (Vehicle: ").append(m.getVehicle().getLicensePlate())
                       .append(") - Expires: ").append(m.getExpirationDate()).append("\n");
            }
            content.append("\n");
        }
        
        if (!result.getOneMonthBefore().isEmpty()) {
            content.append("🔵 UPCOMING: ").append(result.getOneMonthBefore().size())
                   .append(" maintenance(s) expiring in 1 month:\n");
            for (Maintenance m : result.getOneMonthBefore()) {
                content.append("  - ").append(m.getDescription())
                       .append(" (Vehicle: ").append(m.getVehicle().getLicensePlate())
                       .append(") - Expires: ").append(m.getExpirationDate()).append("\n");
            }
            content.append("\n");
        }
        
        content.append("Please log in to your account to manage your maintenances.\n\n");
        content.append("Best regards,\n");
        content.append("Vehicle Maintenance System");
        
        return content.toString();
    }
}

