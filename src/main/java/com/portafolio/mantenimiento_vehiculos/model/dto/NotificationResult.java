package com.portafolio.mantenimiento_vehiculos.model.dto;

import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for notification results
 * Contains information about pending maintenances that need attention
 * @author Andres
 */
public class NotificationResult {
    
    private long expiredCount = 0;
    private List<Maintenance> oneMonthBefore = new ArrayList<>();
    private List<Maintenance> oneWeekBefore = new ArrayList<>();
    private List<Maintenance> oneDayBefore = new ArrayList<>();
    
    public NotificationResult() {}
    
    public boolean hasNotifications() {
        return expiredCount > 0 || !oneMonthBefore.isEmpty() || !oneWeekBefore.isEmpty() || !oneDayBefore.isEmpty();
    }
    
    public long getExpiredCount() {
        return expiredCount;
    }
    
    public void setExpiredCount(long expiredCount) {
        this.expiredCount = expiredCount;
    }
    
    public List<Maintenance> getOneWeekBefore() {
        return oneWeekBefore;
    }
    
    public void setOneWeekBefore(List<Maintenance> oneWeekBefore) {
        this.oneWeekBefore = oneWeekBefore;
    }
    
    public List<Maintenance> getOneDayBefore() {
        return oneDayBefore;
    }
    
    public void setOneDayBefore(List<Maintenance> oneDayBefore) {
        this.oneDayBefore = oneDayBefore;
    }
    
    public List<Maintenance> getOneMonthBefore() {
        return oneMonthBefore;
    }
    
    public void setOneMonthBefore(List<Maintenance> oneMonthBefore) {
        this.oneMonthBefore = oneMonthBefore;
    }
}

