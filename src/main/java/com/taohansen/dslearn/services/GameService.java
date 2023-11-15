package com.taohansen.dslearn.services;

import com.taohansen.dslearn.dto.GameDTO;
import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.entities.Game;
import com.taohansen.dslearn.repositories.GameRepository;
import com.taohansen.dslearn.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Transactional(readOnly = true)
	public List<GameMinDTO> findAll() {
		List<Game> result = gameRepository.findAll();
		return result.stream().map(GameMinDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public GameDTO findById(Long id) {
		Optional<Game> result = gameRepository.findById(id);
		Game entity = result.orElseThrow(() -> new ResourceNotFoundException("Entity Game not found."));
		return new GameDTO(entity);
	}
}
