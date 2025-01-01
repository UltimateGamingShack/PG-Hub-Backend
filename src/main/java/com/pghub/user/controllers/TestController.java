package com.pghub.user.controllers;

import com.pghub.user.dto.ImageModel;
import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.RoleType;
import com.pghub.user.model.User;
import com.pghub.user.services.UserService;
import com.pghub.user.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600) // Allow cross-origin requests from any origin for 1 hour
@RestController // Indicate that this class is a REST controller
@RequestMapping("/api/test") // Base URL for test-related endpoints
public class TestController {
	@Autowired
	UserServiceImpl userServiceImpl;

    @Autowired
    Environment environment;
//	@Autowired
//	Principal principal;
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

	@PreAuthorize("hasRole('USER') or " + // Require USER, MODERATOR, or ADMIN role
			"hasRole('COOK') or " +
			"hasRole('ADMIN')")
	@PostMapping("/upload/{userId}")
	public ResponseEntity<String> upload(ImageModel imageModel, @PathVariable UUID userId) {
		try {
			User user = userServiceImpl.findById(userId);
			return new ResponseEntity<>(userServiceImpl.uploadImage(imageModel, user), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	@GetMapping("/image/{userId}")
//	public ResponseEntity<?> getUserImage(@PathVariable UUID userId,Principal principal) {
//		try {
//			// Verify the logged-in user matches the userId (authorization)
//			String loggedInUserId = principal.getName();
//			String userName = userServiceImpl.getUsernameById(userId);   // getting username by user ID (can be optimized somehow)
//			if (!userName.equals(loggedInUserId)) {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
//			}
//
//			// Fetch the signed URL
//			String signedUrl = userServiceImpl.getSignedImageUrl(userId);
//
//			// Return the signed URL or stream the image (optional)
//			return ResponseEntity.ok(Map.of("url", signedUrl));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching image");
//		}
//	}
@PreAuthorize("hasRole('USER') or " + // Require USER, MODERATOR, or ADMIN role
		"hasRole('COOK') or " +
		"hasRole('ADMIN')")
	@GetMapping("/image-proxy/{userId}")
	public ResponseEntity<InputStreamResource> serveImage(@PathVariable UUID userId, Principal principal) {
		try {
			String loggedInUserId = principal.getName();
			String userName = userServiceImpl.getUsernameById(userId);   // getting username by user ID (can be optimized somehow)
			if (!userName.equals(loggedInUserId)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // change the logic here or the function parameters
			}
//
			// Authenticate and authorize user (similar to previous code)
			// Fetch the signed URL
			String signedUrl = userServiceImpl.getSignedImageUrl(userId);

			// Fetch the image content from the signed URL
			URL url = new URL(signedUrl);
			InputStream inputStream = url.openStream();

			// Stream the image to the client
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG) // Adjust as needed
					.body(new InputStreamResource(inputStream));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{pgId}/{role}")
    public ResponseEntity<List<User>> getUsersByPgIdAndRoleName(
            @PathVariable String pgId,
            @PathVariable String role
    ) throws PgHubException {
        List<User> users = userServiceImpl.getUsersByPgIdAndRoleName(pgId, role);
        if(users==null || users.isEmpty()){
            throw new PgHubException("Service.USERS_NOT_FOUND");
        }

//        String message = environment.getProperty("API.Users_Get")
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

}
