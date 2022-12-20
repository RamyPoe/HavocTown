package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;
import com.project.game.Sprites.CustomEntity;

public class GameScreen implements Screen {

    // To change screens
    MainGame game;

    // For prespective
    private OrthographicCamera cam;
    private Viewport viewport;

    // Background image
    Texture backgroundTexture;

    // Hold all sprites
    private Stage stage;

    // Player
    CustomEntity player;

    public GameScreen(MainGame game) {

        // Save game instance
        this.game = game;

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Create stage
        stage = new Stage(viewport, game.batch);

        // Load background texture
        backgroundTexture = new Texture(Gdx.files.internal("maps/2.jpg"));

        // Create player
        player = new CustomEntity();
        // stage.addActor( (Sprite) player);

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    // Game logic
    public void update(float delta) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.pBody.applyForceX(10);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.pBody.applyForceX(-10);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.pBody.applyForceY(-10);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.pBody.applyForceY(10);
        }

        // To gradually slow down
        // player.pBody.applyFrictionX();

        // Update player position for drawing
        player.update();

    }

    @Override
    public void render(float delta) {

        // Seperate logic from render
        update(delta);

        // Reset screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Prepare to draw
        game.batch.begin();
        
        // Draw
        game.batch.draw(backgroundTexture, 0, 0, MainGame.V_WIDTH, MainGame.V_HEIGHT);
        player.draw(game.batch);

        // Done drawing
        game.batch.end();
        
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);        
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
        // TODO Auto-generated method stub
        
    }
    


}
