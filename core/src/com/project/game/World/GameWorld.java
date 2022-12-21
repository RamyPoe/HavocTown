package com.project.game.World;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.project.game.MainGame;
import com.project.game.Sprites.CustomEntity;

public class GameWorld {
    
    // World structure
    ArrayList<Platform> platforms;
    ShapeRenderer sr;

    // Constructor
    public GameWorld() {

        // Create array
        platforms = new ArrayList<>();

        // Add the platforms
        platforms.add(
            new Platform(0, 0, MainGame.V_WIDTH, 100, false)
        );
        platforms.add(
            new Platform(0, 300, 300, 50, true)
        );

        // Create Shape renderer
        sr = new ShapeRenderer();

    }

    // For player movements
    public void checkPlayer(CustomEntity p) {

        // Check for every platform
        for (Platform plat : platforms) {

            if (p.hBox.isColliding(plat.hBox)) {
                p.pBody.setVy(0);
                p.setPosY(plat.hBox.getTop());
            }

        }

    }

    // Drawing the map
    // TODO: DRAW MAP INSTEAD OF THE PLATFORMS
    public void draw(Batch b) {

        for (Platform p : platforms) {
            b.draw(p.getTexture(), p.x, p.y);
        }
        
    }

    // Load from file
    public void loadMap(int map) {}

}
