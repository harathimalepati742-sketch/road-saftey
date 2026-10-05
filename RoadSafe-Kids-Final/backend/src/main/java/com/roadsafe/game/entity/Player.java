package com.roadsafe.game.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false)
    private int score = 0;

    @Column(nullable = false)
    private int scenariosCompleted = 0;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getScore() { return score; }
    public int getScenariosCompleted() { return scenariosCompleted; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setScore(int score) { this.score = score; }
    public void setScenariosCompleted(int scenariosCompleted) { this.scenariosCompleted = scenariosCompleted; }
}
