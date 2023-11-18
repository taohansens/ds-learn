package com.taohansen.dslearn.services;

import com.taohansen.dslearn.dto.GameDTO;
import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.entities.Game;
import com.taohansen.dslearn.projections.GameMinProjection;
import com.taohansen.dslearn.repositories.GameRepository;
import com.taohansen.dslearn.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
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

	@Transactional(readOnly = true)
	public List<GameMinDTO> findByList(Long listId) {
		List<GameMinProjection> result = gameRepository.searchByList(listId);
		if(result.isEmpty()){
			throw new ResourceNotFoundException("List not found");
		}
		return result.stream().map(GameMinDTO::new).toList();
	}

	@Transactional
	public GameDTO insert(GameDTO dto) {
		Game entity = new Game();
		BeanUtils.copyProperties(dto, entity);
		entity = gameRepository.save(entity);
		return new GameDTO(entity);
	}

	@Transactional
	public GameDTO update(Long id, GameDTO dto) {
		try {
			Game entity = gameRepository.getReferenceById(id);
			BeanUtils.copyProperties(dto, entity);
			entity = gameRepository.save(entity);
			return new GameDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}
}
