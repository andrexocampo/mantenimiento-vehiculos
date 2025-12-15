package com.portafolio.mantenimiento_vehiculos.service;

import com.portafolio.mantenimiento_vehiculos.interfaces.UserRepository;
import com.portafolio.mantenimiento_vehiculos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service for security-related operations
 * @author Andres
 */
@Service
public class SecurityService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Get the currently authenticated user
     * @return User entity of the authenticated user
     * @throws RuntimeException if user is not found
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            throw new RuntimeException("User is not authenticated");
        }
        
        Object principal = auth.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new RuntimeException("Principal is not of type UserDetails");
        }
        
        UserDetails userDetails = (UserDetails) principal;
        return userRepository.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));
    }
}

