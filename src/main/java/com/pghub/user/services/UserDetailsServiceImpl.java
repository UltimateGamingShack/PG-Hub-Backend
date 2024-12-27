package com.pghub.user.services;


import com.pghub.user.model.User;
import com.pghub.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserDetailsService to load user-specific data.
 */
@Service // Indicates that this class is a service component
@Transactional  // Ensures that the method is transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired // Automatically injects UserRepository bean
	UserRepository userRepository;

	/**
	 * Loads user details by username.
	 *
	 * @param username The username of the user.
	 * @return UserDetails containing user information.
	 * @throws UsernameNotFoundException if the user is not found.
	 */
	@Override

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Attempt to find the user by username
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		// Return UserDetails implementation for the found user
		return UserDetailsImpl.build(user);
	}




}
