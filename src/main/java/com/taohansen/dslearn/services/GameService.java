package com.taohansen.dslearn.services;

import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.entities.Game;
import com.taohansen.dslearn.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public List<GameMinDTO> findAll() {
		List<Game> result = gameRepository.findAll();
		return result.stream().map(GameMinDTO::new).toList();
	}
}
