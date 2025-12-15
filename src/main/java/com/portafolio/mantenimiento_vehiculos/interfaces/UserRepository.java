package com.portafolio.mantenimiento_vehiculos.interfaces;

import com.portafolio.mantenimiento_vehiculos.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity
 * @author Andres
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}

