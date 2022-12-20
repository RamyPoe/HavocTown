package com.project.game.Screens;

import com.badlogic.gdx.Screen;
import com.project.game.MainGame;

public class TutorialScreen {
    
    private GameScreen gameScreen;

    public TutorialScreen(MainGame game) {
        gameScreen = new GameScreen(game);
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
    
}
