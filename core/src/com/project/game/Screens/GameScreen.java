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
import com.project.game.World.Parallax;
import com.project.game.World.WorldConfig;

public class GameScreen implements Screen {

    // To change screens
    MainGame game;

    // For prespective
    private OrthographicCamera cam;
    private Viewport viewport;
    public static final float CAM_SPEED_FACTOR = 2.2f;

    // Handle game logic
    GameWorld gameWorld;
    WorldConfig config;

    // Background image
    Parallax backgroundParallax;

    // Player
    CustomEntity player;

    public GameScreen(MainGame game, WorldConfig config) {

        // Save game instance
        this.game = game;
        this.config = config;

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Load background texture
        backgroundParallax = new Parallax(
            "maps/" + config.getTexturePrefix() + "1.png", config.getOffY1(),
            "maps/" + config.getTexturePrefix() + "2.png", config.getOffY2(),
            "maps/" + config.getTexturePrefix() + "3.png", config.getOffY3()
        );

        // Create world
        gameWorld = new GameWorld(config);

        // Create player
        player = new CustomEntity(-25, MainGame.V_HEIGHT*2);

        // Starting camera position
        cam.position.x = (float) player.pBody.getDx();
        cam.position.y = (float) player.pBody.getDy();


    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    // Game logic
    public void update(float delta) {
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.pBody.applyForceY(10);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.passThroughPlatform();
        }

        // Apply gravity
        player.applyGravity();

        // Update player position for drawing
        player.update(delta);

        // Check for world collisions
        gameWorld.checkPlayer(player);


        // TODO: Camera should show all players
        cam.position.x += (player.getX() - cam.position.x) * CAM_SPEED_FACTOR/3 * delta;
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
        backgroundParallax.draw(game.batch, cam);
        player.draw(game.batch);

        // Debug
        // gameWorld.draw(game.batch);

        // Done drawing
        game.batch.end();

        // Transition screen
        if (!game.transition.haveFadedIn)
            game.transition.fadeIn();
        game.transition.draw();
        
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
