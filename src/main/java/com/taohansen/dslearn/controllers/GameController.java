package com.taohansen.dslearn.controllers;

import com.taohansen.dslearn.dto.GameDTO;
import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.entities.Game;
import com.taohansen.dslearn.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping
	public ResponseEntity<List<GameMinDTO>> findAll() {
		List<GameMinDTO> result = gameService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
		GameDTO result = gameService.findById(id);
		return ResponseEntity.ok().body(result);
	}
}
