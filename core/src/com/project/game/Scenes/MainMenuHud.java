/*
* Creates and lays out buttons for main menu,
* as well as the background texture.
* 
* @author  Rameen Popal
* @since   2023-01-31
*/

package com.project.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;

public class MainMenuHud implements Disposable {
    
    // Stage setup
    public Stage stage;
    public Viewport viewport;
    private OrthographicCamera cam;

    // Assets
    public Texture backgroundTexture;

    // Buttons
    public MenuButton customGameButton;
    public MenuButton tutorialGameButton;


    public MainMenuHud(SpriteBatch sb) {

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Stage
        stage = new Stage(viewport, sb);

        // Background image
        backgroundTexture = new Texture(Gdx.files.internal("other/1.jpg"));

        // Buttons
        customGameButton = new MenuButton("CUSTOM GAME", "custom game");
        tutorialGameButton = new MenuButton("TUTORIAL", "learn game controls");

        
        // Table
        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.padRight(20);
        table.padTop(60);

        // Add buttons to table
        table.add(customGameButton).align(Align.right).padBottom(20);
        table.row();
        table.add(tutorialGameButton).align(Align.right);


        // Add table to stage
        stage.addActor(table);

        // So that clicks work
        Gdx.input.setInputProcessor(stage);

    }

    public void draw() {

        // For animations
        stage.act();

        // Get batch ready
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        
        // Draw background image
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, MainGame.V_WIDTH, MainGame.V_HEIGHT);
        stage.getBatch().end();
        
        // Draw GUI
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }

}
