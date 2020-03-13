package com.kevinppaulo.issueTracker.security.auth;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

	private final ApplicationUserJpaRepository appUserJpaRepo;

	@Autowired	
	public ApplicationUserDetailsService(ApplicationUserJpaRepository appUserJpaRepo) {
		this.appUserJpaRepo = appUserJpaRepo;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<ApplicationUser> userByUsername = appUserJpaRepo.findByUsername(username);
		if(!userByUsername.isPresent()) {
			throw new UsernameNotFoundException("Couldn't find username " + username);
		}
		ApplicationUser user = userByUsername.get();

		
		ApplicationUserDetails userDetails = new ApplicationUserDetails(
				user.getUsername(),
				user.getPassword(),
				user.getGrantedAuthorities(),
				user.isAccountNonExpired(),
				user.isCredentialsNonExpired(),
				user.isAccountNonLocked(),
				user.isEnabled());
		return userDetails;
	}
}
