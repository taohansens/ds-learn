package com.taohansen.dslearn.controllers;

import com.taohansen.dslearn.dto.*;
import com.taohansen.dslearn.services.GameListService;
import com.taohansen.dslearn.services.GameService;
import com.taohansen.dslearn.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/lists")
public class GameListController {

	@Autowired
	private GameListService gameListService;

	@Autowired
	private GameService gameService;

	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping
	public ResponseEntity<List<GameListDTO>> findAll() {
		List<GameListDTO> result = gameListService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	@PostMapping
	public ResponseEntity<GameListDTO> insert(@RequestBody GameListDTO body) {
		GameListDTO result = gameListService.insert(body);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	@PostMapping(value = "/{listId}/games")
	public ResponseEntity<List<GameMinDTO>> insertGameOnList(@PathVariable Long listId, @RequestBody InsertGameOnListDTO dto) {
		List<GameMinDTO> result = gameListService.insertOnList(listId, dto.getGameId());
		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping(value = "/{listId}/games")
	public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long listId) {
		List<GameMinDTO> result = gameService.findByList(listId);
		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	@PatchMapping(value = "/{listId}/replacement")
	public ResponseEntity<Void> findByList(@PathVariable Long listId, @RequestBody ReplacementDTO body) {
		gameListService.move(listId, body.getSourceIndex(), body.getDestinationIndex());
		try {
			gameService.findByList(listId);
		} catch (Exception e) {
            throw new ResourceNotFoundException("Entity Game not found.");
		}
        return null;
    }

	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<GameListDTO> update(@PathVariable Long id, @Valid @RequestBody GameListDTO dto) {
		dto = gameListService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}
