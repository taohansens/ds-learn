package com.taohansen.dslearn.controllers;

import com.taohansen.dslearn.dto.GameDTO;
import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.services.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping
	public ResponseEntity<List<GameMinDTO>> findAll() {
		List<GameMinDTO> result = gameService.findAll();
		return ResponseEntity.ok().body(result);
	}
	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
		GameDTO result = gameService.findById(id);
		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	@PostMapping
	public ResponseEntity<GameDTO> insert(@RequestBody @Valid GameDTO body) {
		GameDTO result = gameService.insert(body);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<GameDTO> update(@PathVariable Long id, @Valid @RequestBody GameDTO dto) {
		dto = gameService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}
