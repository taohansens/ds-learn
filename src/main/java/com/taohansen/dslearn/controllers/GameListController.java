package com.taohansen.dslearn.controllers;

import com.taohansen.dslearn.dto.GameListDTO;
import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.dto.InsertGameOnListDTO;
import com.taohansen.dslearn.dto.ReplacementDTO;
import com.taohansen.dslearn.services.GameListService;
import com.taohansen.dslearn.services.GameService;
import com.taohansen.dslearn.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@GetMapping
	public ResponseEntity<List<GameListDTO>> findAll() {
		List<GameListDTO> result = gameListService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping
	public ResponseEntity<GameListDTO> insert(@RequestBody GameListDTO body) {
		GameListDTO result = gameListService.insert(body);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}

	@PostMapping(value = "/{listId}/games")
	public ResponseEntity<List<GameMinDTO>> insertGameOnList(@PathVariable Long listId, @RequestBody InsertGameOnListDTO dto) {
		List<GameMinDTO> result = gameListService.insertOnList(listId, dto.getGameId());
		return ResponseEntity.accepted().body(result);
	}

	@GetMapping(value = "/{listId}/games")
	public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long listId) {
		List<GameMinDTO> result = gameService.findByList(listId);
		return ResponseEntity.ok().body(result);
	}

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
}
