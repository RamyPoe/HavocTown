package com.project.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.project.game.Screens.MainMenuScreen;

public class MainGame extends Game {

	// Screen Constants
	public static final int V_WIDTH = 1536;
	public static final int V_HEIGHT = 864;

	// For drawing images
	public SpriteBatch batch;

	
	@Override
	public void create () {

		// Batch used for drawing
		batch = new SpriteBatch();

		// Start with MainMenuScreen
		setScreen(
			new MainMenuScreen(this)
		);

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
