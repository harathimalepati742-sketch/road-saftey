package com.roadsafe.game.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class GameOption {
    private String text;
    private boolean correct;

    public GameOption() {}

    public GameOption(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() { return text; }
    public boolean isCorrect() { return correct; }

    public void setText(String text) { this.text = text; }
    public void setCorrect(boolean correct) { this.correct = correct; }
}
