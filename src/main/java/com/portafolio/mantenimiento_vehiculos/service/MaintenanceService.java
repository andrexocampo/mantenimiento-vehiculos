package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.MaintenanceRepository;
import com.portafolio.mantenimiento_vehiculos.interfacesService.MaintenanceServiceInterface;
import com.portafolio.mantenimiento_vehiculos.model.Maintenance;
import com.portafolio.mantenimiento_vehiculos.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Maintenance operations
 * @author Andres
 */
@Service
public class MaintenanceService implements MaintenanceServiceInterface{
    @Autowired
    private MaintenanceRepository data;
    
    @Autowired
    private SecurityService securityService;
    
    @Override
    public List<Maintenance> listar(){
        return (List<Maintenance>)data.findAll();
    }
    
    @Override
    public Optional<Maintenance> listarId(int id){
        return data.findById(id);
    }
    
    @Override
    public int save(Maintenance m){
        int res=0;
        Maintenance maintenance=data.save(m);
        if(!maintenance.equals(null)){
            res=1;
        }
        return res;
    }
    
    @Override
    public void delete(int id){
        data.deleteById(id);
    }
    
    @Override
    public List<Maintenance> listAllByUser() {
        User currentUser = securityService.getCurrentUser();
        return data.findByUser(currentUser);
    }
    
    @Override
    public List<Maintenance> listPendingMaintenances() {
        User currentUser = securityService.getCurrentUser();
        return data.findPendingByUser(currentUser);
    }
    
    @Override
    public List<Maintenance> listCompletedMaintenances() {
        User currentUser = securityService.getCurrentUser();
        return data.findCompletedByUser(currentUser);
    }
    
    @Override
    public void markAsPaid(int id) {
        Optional<Maintenance> maintenanceOptional = data.findById(id);
        if (maintenanceOptional.isPresent()) {
            Maintenance maintenance = maintenanceOptional.get();
            // Verify ownership through vehicle
            User currentUser = securityService.getCurrentUser();
            if (maintenance.getVehicle().getUser().getId() == currentUser.getId()) {
                maintenance.setPaid(true);
                if (maintenance.getPaymentDate() == null) {
                    maintenance.setPaymentDate(java.time.LocalDate.now());
                }
                data.save(maintenance);
            }
        }
    }
    
    @Override
    public void revertPayment(int id) {
        Optional<Maintenance> maintenanceOptional = data.findById(id);
        if (maintenanceOptional.isPresent()) {
            Maintenance maintenance = maintenanceOptional.get();
            // Verify ownership through vehicle
            User currentUser = securityService.getCurrentUser();
            if (maintenance.getVehicle().getUser().getId() == currentUser.getId()) {
                maintenance.setPaid(false);
                maintenance.setPaymentDate(null);
                data.save(maintenance);
            }
        }
    }
}

