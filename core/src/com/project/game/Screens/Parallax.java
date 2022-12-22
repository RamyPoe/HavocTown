package com.project.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.project.game.MainGame;

public class Parallax {

    // Hold each layer
    Texture layer1;
    Texture layer2;
    Texture layer3;

    // Constructor
    public Parallax(String path1, String path2, String path3) {

        // Load the images
        layer1 = new Texture(Gdx.files.internal(path1));
        layer2 = new Texture(Gdx.files.internal(path2));
        layer3 = new Texture(Gdx.files.internal(path3));

    }

    // Draw based on where camera is
    public void draw(Batch batch, Camera cam) {
        float xDif = cam.position.x - MainGame.V_WIDTH/2;
        float yDif = cam.position.y - MainGame.V_HEIGHT/2;

        batch.draw(layer1, -175+xDif, yDif/3);
        batch.draw(layer2, -575+xDif*0.6f, yDif/6);
        batch.draw(layer3, 0, 0);
    }


}