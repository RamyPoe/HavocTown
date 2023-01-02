package com.project.game.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Parallax {

    // Hold each layer
    Texture layer1;
    float yOff1;

    Texture layer2;
    float yOff2;

    Texture layer3;
    float yOff3;


    // Constructor
    public Parallax(String path1, float y1, String path2, float y2, String path3, float y3) {

        // Load the images
        layer1 = new Texture(Gdx.files.internal(path1));
        layer2 = new Texture(Gdx.files.internal(path2));
        layer3 = new Texture(Gdx.files.internal(path3));

        // Save offsets for drawing
        yOff1 = y1;
        yOff2 = y2;
        yOff3 = y3;

        // Offset the offsets
        yOff1 -= layer3.getHeight()/2;
        yOff2 -= layer3.getHeight()/2;
        yOff3 -= layer3.getHeight()/2;

    }

    // Draw based on where camera is
    public void draw(Batch batch, Camera cam) {
        float xDif = cam.position.x;
        float yDif = cam.position.y;

        batch.draw(layer1, -layer1.getWidth()/2 + xDif,       yDif/3 + yOff1);
        batch.draw(layer2, -layer2.getWidth()/2 + xDif*0.6f,  yDif/6 + yOff2);
        batch.draw(layer3, -layer3.getWidth()/2,              yOff3);
    }


}