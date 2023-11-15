package com.taohansen.dslearn.repositories;

import com.taohansen.dslearn.entities.GameList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameListRepository extends JpaRepository<GameList, Long> {
}
