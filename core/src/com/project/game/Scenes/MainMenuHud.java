package com.project.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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


    public MainMenuHud(SpriteBatch sb) {

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Stage
        stage = new Stage(viewport, sb);

        // Background image
        backgroundTexture = new Texture(Gdx.files.internal("maps/1.jpg"));


        // Label
        /*
        Label.LabelStyle style = new Label.LabelStyle(bFont, Color.WHITE);
        Label lbl = new Label("HELLO WORLD!", style);
        */

        // Setup for buttons
        MenuButton.loadButtonFont("fonts/menu-font");

        // Buttons
        MenuButton campaignGameButton = new MenuButton("CAMPAIGN", "story mode");
        MenuButton customGameButton = new MenuButton("CUSTOM GAME", "custom game");


        // Button Callbacks
        campaignGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("CAMPAIGN CLICKED!");
            }
        });
        


        // Table
        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.padRight(20);
        table.padTop(20);

        // Spacing from top
        table.row().padTop(200);

        // Add buttons to table
        table.add(campaignGameButton).align(Align.right);
        table.row();
        table.add(customGameButton).align(Align.right);

        // Add table to stage
        stage.addActor(table);

        // So that clicks work
        Gdx.input.setInputProcessor(stage);

    }

    public void draw(float delta) {

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
        MenuButton.dispose();
    }

}
