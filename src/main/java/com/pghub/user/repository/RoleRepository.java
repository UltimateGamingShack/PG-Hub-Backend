package com.pghub.user.repository;


import com.pghub.user.model.Role;
import com.pghub.user.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

/**
 * Repository interface for accessing Role entities in the Postgresql database.
 * It extends JpaRepository, providing CRUD operations for Role objects.
 */
public interface RoleRepository extends JpaRepository<Role, String> {

  /**
   * Find a Role by its name.
   *
   * @param role The name of the role represented as an EmployeeRole enum.
   * @return An Optional containing the Role if found, or empty if not found.
   */
  Optional<Role> findByRole(RoleType role);
}
