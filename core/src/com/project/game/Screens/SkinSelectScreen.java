package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.project.game.MainGame;
import com.project.game.Scenes.SkinSelectHud;

public class SkinSelectScreen implements Screen {

    // To change screens
    private MainGame game;

    // Show players
    private SkinSelectHud skinSelectHud;
    

    public SkinSelectScreen(MainGame game) {

        // Save field
        this.game = game;
        
        // Creat hud
        skinSelectHud = new SkinSelectHud(game.batch);
       
        // Set input handler
        Gdx.input.setInputProcessor(skinSelectHud.stage);

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    public void update() {
         // Skip button check if transitioning
         if (game.transition.active) {
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.transition.fadeOut(MainGame.SCREENS.Tutorial);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.transition.fadeOut(MainGame.SCREENS.MainMenu);
        }
    }

    @Override
    public void render(float delta) {
        
        // Seperate logic from draw
        update();

        // Reset screen
        Gdx.gl.glClearColor(51/255f, 51/255f, 51/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw all stage actors
        skinSelectHud.draw(game.batch);


        // Transition screen fade in
        if (!game.transition.haveFadedIn && !game.transition.active)
            game.transition.fadeIn();
        
        // Draw transition
        game.transition.draw();
        
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        
        skinSelectHud.dispose();
        
    }



}
