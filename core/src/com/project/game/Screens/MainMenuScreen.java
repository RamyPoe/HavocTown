package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
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

        // Fade out if button pressed
        game.transition.fadeOut();
        game.transition.haveFadedIn = false;

    }

    // Changing screens based on button press
    private void checkChangeScreen() {
        
        // Haven't finished fading out
        if (!game.transition.haveFadedOut) {
            return;
        }

        // Don't repeat next frame
        game.transition.haveFadedOut = false;

        // Change to appropriate screen
        switch (buttonPressed) {

            case BTN_CAMPAIGN:
                break;

            case BTN_CUSTOM_GAME:
                break;

            case BTN_TUTORIAL:
                game.setScreenTutorial();                
                break;
                
            case BTN_SETTINGS:
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
        hud.draw(delta);

        // Draw transition
        game.transition.draw();

        // Change screens
        checkChangeScreen();

        
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
        dispose();
        hud.dispose();
    }

}
