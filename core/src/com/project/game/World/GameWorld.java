package com.project.game.World;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.project.game.MainGame;
import com.project.game.Sprites.CustomEntity;
import com.project.game.Sprites.Hitbox;

public class GameWorld {
    
    // World structure
    ArrayList<Platform> platforms;

    // Constructor
    public GameWorld(WorldConfig config) {

        // Create array
        platforms = new ArrayList<>();

        // Add the platforms
        for (Box b : config.getPlatformArray()) {

            platforms.add(
                new Platform(b.x, b.y, b.w, b.h, b.passable)
            );

        }

    }

    // For player movements
    public void checkPlayer(CustomEntity p) {

        // Check for every platform
        for (Platform plat : platforms) {


            // Check for collision
            if (p.hBox.isColliding(plat.hBox)) {

                // If already touching then ignore
                if (plat.touching) { continue; }

                // Determine if collision is from above or below
                Hitbox temp = new Hitbox(p.hBox.x+(p.pBody.getVx() > 0 ? -6 : 6), p.hBox.y-10, p.hBox.w, p.hBox.h);
                // From above, treat as solid
                if ((temp.isColliding(plat.hBox) && !p.wants_to_pass_through) || !plat.passable) {
                    p.pBody.setVy(0);
                    p.setPosY(plat.hBox.getTop());
                    continue;
                }

                // From below, pass through platform
                plat.touching = true;
                p.wants_to_pass_through = false;


            } else {
                plat.touching = false;
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
