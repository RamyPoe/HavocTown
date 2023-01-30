package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;
import com.project.game.MainGame;
import com.project.game.Scenes.MainMenuHud;

public class MainMenuScreen implements Screen {
    
    // For reacting to buttons
    public static enum BUTTONS {BTN_NONE, BTN_CAMPAIGN, BTN_CUSTOM_GAME, BTN_TUTORIAL, BTN_SETTINGS};
    private BUTTONS buttonPressed = BUTTONS.BTN_NONE;

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

        // Button Callbacks
        hud.campaignGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change button pressed
                buttonPressed = BUTTONS.BTN_CAMPAIGN;
            }
        });
        hud.customGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change button pressed
                buttonPressed = BUTTONS.BTN_CUSTOM_GAME;
            }
        });
        hud.tutorialGameButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change button pressed
                buttonPressed = BUTTONS.BTN_TUTORIAL;
            }
        });
        hud.settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SETTINGS CLICKED!");
                // Change button pressed
                buttonPressed = BUTTONS.BTN_SETTINGS;
            }
        });

    }


    @Override
    public void show() {

    }

    // Handles logic
    public void update(float delta) {

        
        // Skip button check if transitioning
        if (game.transition.active) {
            return;
        }

        // If button hasn't been pressed
        if (buttonPressed == BUTTONS.BTN_NONE) {
            return;
        }

        // Check all buttons
        switch(buttonPressed) {

            case BTN_TUTORIAL:
                game.transition.fadeOut(MainGame.SCREENS.SkinSelect);
                break;
            case BTN_CUSTOM_GAME:
                // game.transition.fadeOut(MainGame.SCREENS.Tutorial);
                break;

            default:
                break;

        }


    }

  

    @Override
    public void render(float delta) {

        // Seperate logic from rendering
        update(delta);

        // Reset screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw all stage actors
        hud.draw();

        // Transition screen fade in
        if (!game.transition.haveFadedIn && !game.transition.active)
            game.transition.fadeIn();
        
        // Draw transition
        game.transition.draw();


        
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
        hud.dispose();
    }

}
