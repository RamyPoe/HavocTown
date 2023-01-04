package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.PlayerConfig;
import com.project.game.Scenes.PlayerHud;
import com.project.game.Weapons.Weapon;
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

    // Handle game logic and drawing
    GameWorld gameWorld;
    WorldConfig wConfig;
    
    // Show the player Hud
    PlayerHud playerHuds;

    // Background image
    Parallax backgroundParallax;
    
    // Player
    CustomEntity player, player2;
    PlayerConfig pConfig;

    public GameScreen(MainGame game, WorldConfig wConfig, PlayerConfig pConfig) {

        // Save game instance
        this.game = game;
        this.wConfig = wConfig;
        this.pConfig = pConfig;


        // For prespective
        cam = new OrthographicCamera();
        cam.zoom = 1.0f;
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Load background texture
        backgroundParallax = new Parallax(
            "maps/" + wConfig.getTexturePrefix() + "1.png", wConfig.getOffY1(),
            "maps/" + wConfig.getTexturePrefix() + "2.png", wConfig.getOffY2(),
            "maps/" + wConfig.getTexturePrefix() + "3.png", wConfig.getOffY3()
        );

        // Create world
        gameWorld = new GameWorld(wConfig);

        // So that weapons can create throwables
        Weapon.setWorld(gameWorld);

        // Create player
        player = new CustomEntity(-25, MainGame.V_HEIGHT*2, pConfig);
        gameWorld.addPlayer(player);


        // Player 2 for testing
        PlayerConfig ccc = new PlayerConfig();
        ccc.faceSkin = 1;
        ccc.hatSkin = 4;
        ccc.playerColorNumber = 1;
        ccc.shirtSkin = 4;

        player2 = new CustomEntity(-125, MainGame.V_HEIGHT*2, pConfig);
        gameWorld.addPlayer(player2);

        // Starting camera position
        cam.position.x = (float) player.pBody.getDx();
        cam.position.y = (float) player.pBody.getDy();

        
        // Create the player hud
        playerHuds = new PlayerHud(game.batch, gameWorld.getPlayerArray());


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

        if (! (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            player.applying_force = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.passThroughPlatform();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            player.shoot(gameWorld.getBulletArray());
        }


        // PLAYER 2
        //============================================
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player2.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player2.moveRight();
        }

        if (! (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D))) {
            player2.applying_force = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player2.jump();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player2.passThroughPlatform();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player2.shoot(gameWorld.getBulletArray());
        }

        //============================================


        // Check for world collisions
        gameWorld.step(delta);


        // Average of all players
        cam.position.x += (MainGame.clamp(gameWorld.averagePlayerX(), -500, 500) - cam.position.x) * CAM_SPEED_FACTOR/3 * delta;
        cam.position.y += (MainGame.clamp(gameWorld.averagePlayerY(), 100, 500) - cam.position.y) * CAM_SPEED_FACTOR * delta;


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
        
        // Draw background
        backgroundParallax.draw(game.batch, cam);

        // Draw players, bullets, (platforms)
        gameWorld.draw(game.batch);

        // Done drawing
        game.batch.end();

        // Draw the hud
        playerHuds.draw();

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
