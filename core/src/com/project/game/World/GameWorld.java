package com.project.game.World;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.Hitbox;
import com.project.game.Weapons.GunBullet;

public class GameWorld {
    
    // World structure
    private ArrayList<Platform> platforms;

    // Players
    private ArrayList<CustomEntity> players;

    // Bullets
    private ArrayList<GunBullet> bullets;


    // Constructor
    public GameWorld(WorldConfig config) {

        // Create arrays
        platforms = new ArrayList<>();
        players = new ArrayList<>();
        bullets = new ArrayList<>();

        // Add the platforms
        for (Box b : config.getPlatformArray()) {

            platforms.add(
                new Platform(b.x, b.y, b.w, b.h, b.passable)
            );

        }

    }

    // Add a player to the world
    public void addPlayer(CustomEntity p) {
        players.add(p);
    }

    // For the weapons
    public ArrayList<GunBullet> getBulletArray() {
        return bullets;
    }

    // Update the world
    public void step(float delta) {

        // Apply gravity and check players with map
        for (CustomEntity p : players) {
            p.applyGravity();
            p.update();
            checkPlayer(p);
        }


        // Check bullets with players
        for (CustomEntity p : players) {

            // For every bullet
            ListIterator<GunBullet> bs = bullets.listIterator();
            while (bs.hasNext()) {
                GunBullet b = bs.next();

                // Bullet Hit the player
                if (p.hBox.isColliding(b.hBox)) {

                    // Apply bullet force
                    p.bulletHit(b);

                    // Delete the bullet, spawn blood
                    bs.remove();
                }
            }

        }

        // Move the bullets
        ListIterator<GunBullet> bs = bullets.listIterator();
        while (bs.hasNext()) {
            GunBullet b = bs.next();

            if (!b.update()) {
                bs.remove();
            }
        }



    }

    // For player movements
    private void checkPlayer(CustomEntity p) {

        // Assume we don't touch a platform
        p.grounded = false;

        // Check for every platform
        for (Platform plat : platforms) {


            // Check for collision
            if (p.hBox.isColliding(plat.hBox)) {

                // If already touching then ignore
                if (plat.touching) { continue; }

                // Determine if collision is from above or below
                Hitbox temp = new Hitbox(p.hBox.x+(p.pBody.getVx() > 0 ? -6 : 6), p.hBox.y-20, p.hBox.w, p.hBox.h);
                // From above, treat as solid
                if ((temp.isColliding(plat.hBox) && !p.wants_to_pass_through) || !plat.passable) {

                    // Reset jumps and velocity
                    p.grounded = true;
                    p.jumps = CustomEntity.MAX_JUMPS;
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
