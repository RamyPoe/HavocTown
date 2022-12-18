package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;
import com.project.game.MainGame;
import com.project.game.Scenes.MainMenuHud;

public class MainMenuScreen implements Screen {
    
    // To change screens
    private MainGame game;

    // For button interaction
    MainMenuHud hud;

    // For prespective
    private OrthographicCamera cam;
    private Viewport viewport;

    public MainMenuScreen(MainGame game) {
        // Hold onto game instance
        this.game = game;

        // Add Hud
        hud = new MainMenuHud(game.batch);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Reset screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw all stage actors
        hud.draw(delta);

        
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}
