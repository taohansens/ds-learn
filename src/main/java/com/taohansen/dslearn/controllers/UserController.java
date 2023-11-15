package com.taohansen.dslearn.controllers;

import com.taohansen.dslearn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taohansen.dslearn.dto.UserDTO;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;
	
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping(value = "/profile")
	public ResponseEntity<UserDTO> getProfile() {
		UserDTO dto = service.getProfile();
		return ResponseEntity.ok(dto);
	}
}
