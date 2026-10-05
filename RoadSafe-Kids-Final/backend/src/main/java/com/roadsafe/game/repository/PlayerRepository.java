package com.roadsafe.game.repository;

import com.roadsafe.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findTop10ByOrderByScoreDesc();
}
