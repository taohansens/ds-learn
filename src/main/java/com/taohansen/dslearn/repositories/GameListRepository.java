package com.taohansen.dslearn.repositories;

import com.taohansen.dslearn.entities.GameList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GameListRepository extends JpaRepository<GameList, Long> {

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_belonging SET position = :newPosition WHERE list_id = :listId AND game_id = :gameId")
    void updateBelongingPosition(Long listId, Long gameId, Integer newPosition);

    @Modifying
    @Query(value = "INSERT INTO tb_belonging (list_id, game_id, position) " +
            "VALUES (:listId, :gameId, COALESCE((SELECT MAX(position) + 1 FROM tb_belonging WHERE list_id = :listId), 1))", nativeQuery = true)
    void insertGameOnList(Long listId, Long gameId);
}
