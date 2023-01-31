
package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;

public class TutorialScreen implements Screen {

    // Texture that explains game
    Texture imgTexture;

    // For displaying
    OrthographicCamera cam;
    FitViewport vp;

    // Main game
    MainGame game;


    // Constructor
    public TutorialScreen(MainGame game) {

        // Save field
        this.game = game;

        // Load the image
        imgTexture = new Texture(Gdx.files.internal("other/tutorial.png"));

        // Camera for drawing
        cam = new OrthographicCamera();
        cam.position.x = MainGame.V_WIDTH/2;
        cam.position.y = MainGame.V_HEIGHT/2;
        vp = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);
        vp.update(MainGame.V_WIDTH, MainGame.V_HEIGHT);  
        cam.update();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    // Seperate logic from drawing
    public void update() {

        // Skip button check if transitioning
        if (game.transition.active) {
            return;
        }

        // Check for escape key
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.transition.fadeOut(MainGame.SCREENS.MainMenu);
        }
        
    }

    @Override
    public void render(float delta) {
        // Seperate logic from drawing
        update();    

        // Reset screen
        Gdx.gl.glClearColor(91/255f, 91/255f, 91/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set projection
        game.batch.setProjectionMatrix(cam.combined);

        // Draw
        game.batch.begin();
        game.batch.draw(imgTexture, 0, 0);
        game.batch.end();

        // Transition screen fade in
        if (!game.transition.haveFadedIn && !game.transition.active)
            game.transition.fadeIn();
        
        // Draw transition
        game.transition.draw();
        
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
        cam.update();
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
        imgTexture.dispose();
    }

}
