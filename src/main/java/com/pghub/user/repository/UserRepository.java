package com.pghub.user.repository;


import com.pghub.user.model.Role;
import com.pghub.user.model.RoleType;
import com.pghub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing User entities in the Postgresql database.
 * It extends JpaRepository, providing CRUD operations for User objects.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * Find a User by their username.
   *
   * @param username The username of the user.
   * @return An Optional containing the User if found, or empty if not found.
   */
  Optional<User> findByUsername(String username);

  /**
   * Check if a username already exists in the database.
   *
   * @param username The username to check.
   * @return A Boolean indicating whether the username exists (true) or not (false).
   */
  Boolean existsByUsername(String username);

  /**
   * Check if an email already exists in the database.
   *
   * @param email The email to check.
   * @return A Boolean indicating whether the email exists (true) or not (false).
   */
  Boolean existsByEmail(String email);


  Optional<User> findByEmail(String email);




  /**
   *
   * @param phoneNo The phoneNo to check
   * @return A Boolean indicating whether the phone number exists (true) or not (false)
   */
  Boolean existsByPhoneNo(String phoneNo);

  /**
   *
   * @param PgId The PG Id used to fetch all users of a pg
   * @return all users in a particular PG
   */
  List<User> findByPgId(Integer PgId);
//  @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role = :roleType AND u.pgId = :pgId")
//  List<User> findUsersByRoleAndPgId(@Param("roleType") RoleType roleType, @Param("pgId") Integer pgId);
//
  @Query("Select u.username from User u where u.id = :id")
String findUsernameById(@Param("id") UUID id);

  @Query("SELECT u FROM User u JOIN u.roles r WHERE u.pgId = :pgId AND r.role = :roleName")
  List<User> findUsersByPgIdAndRoleName(@Param("pgId") String pgId, @Param("roleName") RoleType roleName);
}

