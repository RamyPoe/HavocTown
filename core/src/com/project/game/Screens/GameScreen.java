package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;
import com.project.game.Sprites.CustomEntity;
import com.project.game.World.GameWorld;

public class GameScreen implements Screen {

    // To change screens
    MainGame game;

    // For prespective
    private OrthographicCamera cam;
    private Viewport viewport;
    public static final float CAM_SPEED_FACTOR = 2.2f;

    // Handle game logic
    GameWorld gameWorld;

    // Background image
    Texture backgroundTexture;

    // Player
    CustomEntity player;

    public GameScreen(MainGame game) {

        // Save game instance
        this.game = game;

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Load background texture
        backgroundTexture = new Texture(Gdx.files.internal("maps/2.jpg"));

        // Create world
        gameWorld = new GameWorld();

        // Create player
        player = new CustomEntity();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    // Game logic
    public void update(float delta) {
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
            // cam.zoom -= 0.002f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
            // cam.zoom += 0.002f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.pBody.applyForceY(4);
        }

        // Apply gravity
        player.applyGravity();

        // Update player position for drawing
        player.update(delta);

        // Check for world collisions
        gameWorld.checkPlayer(player);


        // Camera should show all players
        cam.position.x += (player.getX() - cam.position.x) * CAM_SPEED_FACTOR * delta;
        cam.position.y += (player.getY() - cam.position.y) * CAM_SPEED_FACTOR * delta;

    }

    @Override
    public void render(float delta) {

        // Seperate logic from render
        update(delta);

        // Camera
        cam.update();

        // Reset screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Prepare to draw
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        
        // Draw
        game.batch.draw(backgroundTexture, 0, 0, MainGame.V_WIDTH, MainGame.V_HEIGHT);
        player.draw(game.batch);
        gameWorld.draw(game.batch);

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
