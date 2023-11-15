package com.taohansen.dslearn.services;

import java.util.List;

import com.taohansen.dslearn.entities.Role;
import com.taohansen.dslearn.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taohansen.dslearn.dto.UserDTO;
import com.taohansen.dslearn.projections.UserDetailsProjection;
import com.taohansen.dslearn.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public UserDTO getProfile() {
		return new UserDTO(authService.authenticated());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("Email not found");
		}
		
		User user = new User();
		user.setEmail(result.get(0).getUsername());
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}
}
