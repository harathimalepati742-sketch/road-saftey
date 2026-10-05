package com.roadsafe.game.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scenarios")
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String situation;

    @Column(nullable = false)
    private String emoji;

    @Column(nullable = false, length = 1000)
    private String explanation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "scenario_options", joinColumns = @JoinColumn(name = "scenario_id"))
    private List<GameOption> options = new ArrayList<>();

    public Scenario() {}

    public Scenario(String title, String situation, String emoji, String explanation, List<GameOption> options) {
        this.title = title;
        this.situation = situation;
        this.emoji = emoji;
        this.explanation = explanation;
        this.options = options;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSituation() { return situation; }
    public String getEmoji() { return emoji; }
    public String getExplanation() { return explanation; }
    public List<GameOption> getOptions() { return options; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setSituation(String situation) { this.situation = situation; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public void setOptions(List<GameOption> options) { this.options = options; }
}
