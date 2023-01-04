package com.project.game.World;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.Hitbox;
import com.project.game.Weapons.GunBullet;
import com.project.game.Weapons.GunLibrary;
import com.project.game.Weapons.Throwable;

public class GameWorld {
    
    // World structure
    private ArrayList<Platform> platforms;

    // Players
    private ArrayList<CustomEntity> players;

    // Bullets
    private ArrayList<GunBullet> bullets;

    // Pistol when reloading
    private ArrayList<Throwable> throwables;

    // Supply drops
    private ArrayList<SupplyDrop> supplydrops;


    // Constructor
    public GameWorld(WorldConfig config) {

        // Create arrays
        platforms = new ArrayList<>();
        players = new ArrayList<>();
        bullets = new ArrayList<>();
        throwables = new ArrayList<>();
        supplydrops = new ArrayList<>();

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

    // Add a throwable to the world
    public void addThrowable(Throwable t) {
        throwables.add(t);
    }

    // For the camera
    public float averagePlayerX() {
        float av = 0;
        for (CustomEntity p : players) { av += p.getX(); }
        return av / players.size();
    }
    public float averagePlayerY() {
        float av = 0;
        for (CustomEntity p : players) { av += p.getY(); }
        return av / players.size();
    }

    // For spawning
    public float randomSpawnX() {

        // Get random x coordinate
        return (float) (Math.random()*800 - 400) - SupplyDrop.supplyTexture.getWidth();
    }
    
    // Create the supply drop
    public void addRandomSupplyDrop() {
        supplydrops.add(
            new SupplyDrop( randomSpawnX(), MainGame.GAME_MAX_TOP)
        );
    }

    // Return bullet array for the weapons
    public ArrayList<GunBullet> getBulletArray() {
        return bullets;
    }

    // Get players for the hud
    public ArrayList<CustomEntity> getPlayerArray() {
        return players;
    }

    // Update the world
    public void step(float delta) {

        // Apply gravity and check players with map
        for (CustomEntity p : players) {
            p.applyGravity();
            p.applyFrictionX();
            p.update();
            checkPlayerPlatform(p);

            // Make sure player is respecting bounds, otherwise respawn them
            if (p.getX() < MainGame.GAME_MAX_LEFT ||
                p.getX() + CustomEntity.P_WIDTH > MainGame.GAME_MAX_RIGHT ||
                p.getY() < MainGame.GAME_MAX_BOTTOM
                ) {

                    p.setPosX(randomSpawnX());
                    p.setPosY(MainGame.GAME_MAX_TOP);
                    p.reset();

                }
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


        // Check throwables with players
        for (CustomEntity p : players) {

            for (Throwable t : throwables) {

                // Already collided
                if (t.collided) { continue; }

                // Only collide if throwable is falling
                if (t.getVel().y < 0 && p.hBox.isColliding(t.hBox)) {
                    
                    // Apply force
                    p.pBody.applyForceX(Throwable.THROW_FORCE * (t.flip ? 1 : -1));

                    // Set as collided
                    t.setCollided();

                }

            }

        }

        // Move the throwables
        ListIterator<Throwable> ts = throwables.listIterator();
        while(ts.hasNext()) {
            Throwable t = ts.next();

            if (!t.update()) {
                ts.remove();
            }
        }

        // Check for supply drop collisions with platforms/players
        Iterator<SupplyDrop> sdIt = supplydrops.listIterator();
        while (sdIt.hasNext()) {

            // Get supply drop
            SupplyDrop sd = sdIt.next();
            
            // Move it down
            sd.update();

            // With platforms
            for (Platform pl : platforms) {

                if (pl.hBox.isColliding(sd.hBox)) {

                    sd.stopMoving();
                    sd.setY(pl.hBox.getTop());

                }

            }


            // With players
            for (CustomEntity p : players) {

                // Give a random gun when player gets supply drop
                if (p.hBox.isColliding(sd.hBox)) {

                    p.giveWeapon(
                        SupplyDrop.getWeaponReward(p)
                    );

                    // Remove from list
                    sdIt.remove();

                }

            }


        }


        // Randomly spawn a supply drop
        boolean spawn = ((int) (Math.random() * SupplyDrop.SPAWN_CHANCE)) == 0;
        if (spawn) {
            addRandomSupplyDrop();
        }

    }

    // For player movements
    private void checkPlayerPlatform(CustomEntity p) {

        // Assume we don't touch a platform
        p.grounded = false;

        // Check for every platform
        for (Platform plat : platforms) {

            // Is the right one
            if (p.touching != null)
                if (!p.touching.equals(plat))
                    continue;

            // Check for collision
            if (p.getFeetHitbox().isColliding(plat.hBox)) {

                // If already touching then ignore
                if (p.touching != null) { continue; }

                // Determine if collision is from above or below
                Hitbox temp = new Hitbox(p.getFeetHitbox().x+(p.pBody.getVx() > 0 ? -6 : 6), p.getFeetHitbox().y-20, p.getFeetHitbox().w, p.getFeetHitbox().h);
                // From above, treat as solid
                if ((temp.isColliding(plat.hBox) && !p.wants_to_pass_through) || (!plat.passable && temp.isColliding(plat.hBox))) {

                    // Reset jumps and velocity
                    p.grounded = true;
                    p.jumps = CustomEntity.MAX_JUMPS;
                    p.pBody.setVy(0);
                    p.setPosY(plat.hBox.getTop());
                    continue;
                }


                // From below, pass through platform
                p.touching = plat;
                p.wants_to_pass_through = false;


            } else {
                p.touching = null;
            }

        }

    }

    // Drawing the map
    public void draw(Batch b) {
        
        // Draw supply drops
        for (SupplyDrop sd : supplydrops) {
            sd.draw(b);
        }

        // Draw bullets
        for (GunBullet bu : getBulletArray()) {
            bu.draw(b);
        }
        
        // Draw player
        for (CustomEntity p : players) {
            p.draw(b);
        }

        // Draw throwables
        for (Throwable t : throwables) {
            t.draw(b);
        }

        

        // Debug show platform hitboxes
        /*
        for (Platform p : platforms) {
            b.draw(p.getTexture(), p.x, p.y);
        }
        */

    }

}
