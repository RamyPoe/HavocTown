package com.project.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.project.game.Player.PlayerConfig;
import com.project.game.Screens.GameScreen;
import com.project.game.Screens.MainMenuScreen;
import com.project.game.Transition.Transition;
import com.project.game.World.WorldConfig;

public class MainGame extends Game {

	// For switching screens
	public static enum SCREENS {MainMenu, Tutorial};
	private static SCREENS cur_screen = SCREENS.MainMenu;
	private static SCREENS new_screen = SCREENS.MainMenu;

	// Player configurations
	public PlayerConfig playerConfiguration;
	
	// Screen Constants
	public static final int V_WIDTH = 1536;
	public static final int V_HEIGHT = 864;
	
	public static final int GAME_MAX_RIGHT 	=  1400;
	public static final int GAME_MAX_LEFT 	= -1400;
	public static final int GAME_MAX_TOP 	=  1000;
	public static final int GAME_MAX_BOTTOM = -400 ;
	
	// For transition elements
	public Transition transition;
	
	// For drawing images
	public SpriteBatch batch;
	
	
	@Override
	public void create () {
		
		// Batch used for drawing
		batch = new SpriteBatch();
		
		// For animation between screens
		transition = new Transition(60);
		transition.setGame(this);
		
		// For player skin selection
		playerConfiguration = new PlayerConfig();

		// Start with MainMenuScreen
		setScreen(
			new MainMenuScreen(this)
		);

	}
		
		
	public void setGameScreen(SCREENS s) {
		MainGame.new_screen = s;
	}

	
	// So we can draw things
	public static Texture createTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
		
		
	@Override
	public void render () {
		
		// Show the fps
		Gdx.graphics.setTitle("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));

		// See if screen need to be changed
		if (cur_screen != new_screen) {

			// Dispose old screen
			this.getScreen().dispose();
			
			// Change the screen
			switch (new_screen) {
				case Tutorial:

					// Get config for screen
					/* 
					WorldConfig config = new WorldConfig();
					config.setTexturePrefix("a");
					config.setOffY(0, 600, 0);

					// config.addPlatform(-825, 460, 1640, 40, false);
					// config.addPlatform(-780, 630, 1350, 40, true);
					// config.addPlatform(-710, 800, 1060, 40, true);
					// config.addPlatform(-670, 962, 730, 40, true);
					config.addPlatform(-790, 1000, 1600, 40, false);
					config.addPlatform(-615, 1200, 1200, 40, true);


					// WorldConfig.writeConfigFile(config);
					*/
					
					playerConfiguration.hatSkin = -1;
					playerConfiguration.shirtSkin = -1;
					playerConfiguration.faceSkin = 3;
					playerConfiguration.playerColorNumber = 0;

					WorldConfig wConfig = WorldConfig.readConfigFile("a");

					setScreen(
						new GameScreen(
							this,
							wConfig,
							playerConfiguration
						)
					);
				
					break;
				
				case MainMenu:

					setScreen(
						new MainMenuScreen(this)
					);

					break;
			}
	
			// So we don't repeat next frame
			cur_screen = new_screen;

		}

		// Draw the current screen
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}

	// Get current time
	public static long getTimeMs() {
		return System.nanoTime() / 1_000_000;
	}

	// Keep value within bounds
	public static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

}
