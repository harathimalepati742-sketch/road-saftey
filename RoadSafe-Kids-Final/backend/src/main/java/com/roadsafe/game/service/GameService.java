package com.roadsafe.game.service;

import com.roadsafe.game.dto.*;
import com.roadsafe.game.entity.Player;
import com.roadsafe.game.entity.Scenario;
import com.roadsafe.game.exception.NotFoundException;
import com.roadsafe.game.repository.PlayerRepository;
import com.roadsafe.game.repository.ScenarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {
    private final PlayerRepository playerRepository;
    private final ScenarioRepository scenarioRepository;

    public GameService(PlayerRepository playerRepository, ScenarioRepository scenarioRepository) {
        this.playerRepository = playerRepository;
        this.scenarioRepository = scenarioRepository;
    }

    public List<Scenario> getScenarios() {
        return scenarioRepository.findAll();
    }

    public Scenario getScenario(Long id) {
        return scenarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Scenario not found: " + id));
    }

    public Player createPlayer(CreatePlayerRequest request) {
        return playerRepository.save(new Player(request.name().trim()));
    }

    @Transactional
    public AnswerResponse answer(AnswerRequest request) {
        Player player = playerRepository.findById(request.playerId())
                .orElseThrow(() -> new NotFoundException("Player not found: " + request.playerId()));

        Scenario scenario = getScenario(request.scenarioId());

        int points = Boolean.TRUE.equals(request.correct()) ? 10 : -5;
        player.setScore(Math.max(0, player.getScore() + points));
        player.setScenariosCompleted(player.getScenariosCompleted() + 1);
        playerRepository.save(player);

        String message = Boolean.TRUE.equals(request.correct())
                ? "Excellent! You followed the safe road rule."
                : "Oops! That action is unsafe. Learn the safety rule below.";

        return new AnswerResponse(
                Boolean.TRUE.equals(request.correct()),
                points,
                player.getScore(),
                message,
                scenario.getExplanation()
        );
    }

    public List<Player> leaderboard() {
        return playerRepository.findTop10ByOrderByScoreDesc();
    }
}
