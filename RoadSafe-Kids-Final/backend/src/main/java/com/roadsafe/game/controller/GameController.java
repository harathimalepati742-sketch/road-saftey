package com.roadsafe.game.controller;

import com.roadsafe.game.dto.*;
import com.roadsafe.game.entity.Player;
import com.roadsafe.game.entity.Scenario;
import com.roadsafe.game.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/scenarios")
    public List<Scenario> scenarios() {
        return gameService.getScenarios();
    }

    @GetMapping("/scenarios/{id}")
    public Scenario scenario(@PathVariable Long id) {
        return gameService.getScenario(id);
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public Player createPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        return gameService.createPlayer(request);
    }

    @PostMapping("/game/answer")
    public AnswerResponse answer(@Valid @RequestBody AnswerRequest request) {
        return gameService.answer(request);
    }

    @GetMapping("/leaderboard")
    public List<Player> leaderboard() {
        return gameService.leaderboard();
    }
}
