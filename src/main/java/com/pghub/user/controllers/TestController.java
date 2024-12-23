package com.pghub.user.controllers;

import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.RoleType;
import com.pghub.user.model.User;
import com.pghub.user.services.UserService;
import com.pghub.user.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600) // Allow cross-origin requests from any origin for 1 hour
@RestController // Indicate that this class is a REST controller
@RequestMapping("/api/test") // Base URL for test-related endpoints
public class TestController {
	@Autowired
	UserServiceImpl userServiceImpl;
	/**
	 * Public endpoint that can be accessed without any authentication.
	 *
	 * @return A string message indicating public content.
	 */
	@GetMapping("/all") // Map GET requests to "/api/test/all"
	public String allAccess() {
		return "Public Content."; // Return a message accessible by anyone
	}

	/**
	 * Endpoint accessible only to users with USER, MODERATOR, or ADMIN roles.
	 *
	 * @return A string message indicating user content.
	 */
	@GetMapping("/user/{PgId}") // Map GET requests to "/api/test/user"
	@PreAuthorize("hasRole('USER') or " + // Require USER, MODERATOR, or ADMIN role
			"hasRole('COOK') or " +
			"hasRole('ADMIN')")
	public ResponseEntity<List<User>> userAccess(@PathVariable Integer PgId) throws PgHubException {
		List<User> users = userServiceImpl.getUsersByPgIdAndRole(PgId);
		return new ResponseEntity<>(users , HttpStatus.OK); // Return a message accessible by users with the required roles
	}

	/**
	 * Endpoint accessible only to users with the MODERATOR role.
	 *
	 * @return A string message indicating moderator board content.
	 */
	@GetMapping("/mod") // Map GET requests to "/api/test/mod"
	@PreAuthorize("hasRole('COOK')") // Require MODERATOR role
	public String moderatorAccess() {
		return "Moderator Board."; // Return a message accessible by moderators
	}

	/**
	 * Endpoint accessible only to users with the ADMIN role.
	 *
	 * @return A string message indicating admin board content.
	 */
	@GetMapping("/admin") // Map GET requests to "/api/test/admin"
	@PreAuthorize("hasRole('ADMIN')") // Require ADMIN role
	public String adminAccess() {
		return "Admin Board."; // Return a message accessible by admins
	}
}
