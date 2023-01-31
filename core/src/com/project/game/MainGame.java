/*
* The main class where the program begins. It 
* delegates screen behaviour and holds common
* variables as class elements.
* 
* @author  Rameen Popal
* @since   2023-01-31
*/

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
import com.project.game.Screens.SkinSelectScreen;
import com.project.game.Screens.TutorialScreen;
import com.project.game.Transition.Transition;
import com.project.game.World.WorldConfig;

public class MainGame extends Game {

	// For switching screens
	public static enum SCREENS {MainMenu, Game, SkinSelect, Tutorial};
	private static SCREENS cur_screen = SCREENS.MainMenu;
	private static SCREENS new_screen = SCREENS.MainMenu;

	// Player configurations
	public static PlayerConfig playerConfig1;
	public static PlayerConfig playerConfig2;
	public static String mapChosen = "b";
	
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
		playerConfig1 = new PlayerConfig();
		playerConfig2 = new PlayerConfig();

		// Default skins
		playerConfig1.hatSkin = -1;
		playerConfig1.shirtSkin = -1;
		playerConfig1.faceSkin = 3;
		playerConfig1.playerColorNumber = 0;

		playerConfig2.hatSkin = -1;
		playerConfig2.shirtSkin = -1;
		playerConfig2.faceSkin = 3;
		playerConfig2.playerColorNumber = 0;

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
				case Game:

					WorldConfig wConfig = WorldConfig.readConfigFile(mapChosen);

					setScreen(
						new GameScreen(
							this,
							wConfig
						)
					);
				
					break;
				
				case MainMenu:

					setScreen(
						new MainMenuScreen(this)
					);

					break;
				
				case SkinSelect:

					setScreen(
						new SkinSelectScreen(this)
					);

					break;
				
				case Tutorial:
					
					setScreen(
						new TutorialScreen(this)
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
