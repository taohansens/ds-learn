package com.taohansen.dslearn.services;

import com.taohansen.dslearn.dto.GameListDTO;
import com.taohansen.dslearn.dto.GameMinDTO;
import com.taohansen.dslearn.entities.Game;
import com.taohansen.dslearn.entities.GameList;
import com.taohansen.dslearn.projections.GameMinProjection;
import com.taohansen.dslearn.repositories.GameListRepository;
import com.taohansen.dslearn.repositories.GameRepository;
import com.taohansen.dslearn.services.exceptions.DatabaseException;
import com.taohansen.dslearn.services.exceptions.MoveNotAllowedException;
import com.taohansen.dslearn.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

	@Autowired
	private GameListRepository gameListRepository;

	@Autowired
	private GameRepository gameRepository;

	@Transactional(readOnly = true)
	public List<GameListDTO> findAll() {
		List<GameList> result = gameListRepository.findAll();
		return result.stream().map(GameListDTO::new).toList();
	}

	@Transactional
	public GameListDTO insert(GameListDTO dto) {
		GameList entity = new GameList();
		entity.setName(dto.getName());
		entity = gameListRepository.save(entity);
		return new GameListDTO(entity);
	}

	@Transactional
	public List<GameMinDTO> insertOnList(Long listId, Long gameId) {
		if (!gameRepository.existsById(gameId))
			throw new ResourceNotFoundException("Game id (" + gameId + ") not found.");
		if (!gameListRepository.existsById(listId))
			throw new ResourceNotFoundException("List id (" + gameId + ") not found.");

		Game game = gameRepository.getReferenceById(gameId);
		GameList gameList = gameListRepository.getReferenceById(listId);
		try {
			gameListRepository.insertGameOnList(gameList.getId(), game.getId());
		} catch (DataIntegrityViolationException e){
			throw new DatabaseException("Integrity Violation ListId="+ listId + " gameId="+gameId);
		}

		List<GameMinProjection> result = gameRepository.searchByList(listId);
		return result.stream().map(GameMinDTO::new).toList();
	}

	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex){
		List<GameMinProjection> list = gameRepository.searchByList(listId);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("List not found");
		}
		int listSize = list.size() - 1;

		if(sourceIndex == destinationIndex || sourceIndex < 0  || destinationIndex < 0 ||
				destinationIndex > listSize ||  sourceIndex > listSize) {
			throw new MoveNotAllowedException("Erro ao ordenar.");
		}

		int min = Math.min(sourceIndex, destinationIndex), max = Math.max(sourceIndex, destinationIndex);

		GameMinProjection obj = list.remove(sourceIndex);
		list.add(destinationIndex, obj);

		for (int i = min; i <= max; i++){
			gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
		}
	}

	@Transactional
	public GameListDTO update(Long id, GameListDTO dto) {
		try {
			GameList entity = gameListRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = gameListRepository.save(entity);
			return new GameListDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}
}
