package com.project.game.World;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.Hitbox;
import com.project.game.Weapons.GunBullet;
import com.project.game.Weapons.Throwable;
import com.badlogic.gdx.utils.Array;

public class GameWorld implements Disposable {
    
    // World structure
    private Array<Platform> platforms;
    private Hitbox temp = new Hitbox(0, 0, CustomEntity.P_WIDTH, 30);

    // Players
    private Array<CustomEntity> players;

    // Bullets
    private Array<GunBullet> bullets;

    // Pistol when reloading
    private Array<Throwable> throwables;

    // Supply drops
    private Array<SupplyDrop> supplydrops;


    // Constructor
    public GameWorld(WorldConfig config) {

        // Create arrays
        platforms = new Array<>(false, 10, Platform.class);
        players = new Array<>(false, 4, CustomEntity.class);
        bullets = new Array<>(false, 16, GunBullet.class);
        throwables = new Array<>(false, 4, Throwable.class);
        supplydrops = new Array<>(false, 4, SupplyDrop.class);

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
        return av / players.size;
    }
    public float averagePlayerY() {
        float av = 0;
        for (CustomEntity p : players) { av += p.getY(); }
        return av / players.size;
    }

    // For spawning
    public float randomSpawnX() {

        // Get random x coordinate
        return (float) (Math.random()*800 - 400) - SupplyDrop.TEXTURE_WIDTH;
    }
    
    // Create the supply drop
    public void addRandomSupplyDrop() {
        supplydrops.add(
            new SupplyDrop( randomSpawnX(), MainGame.GAME_MAX_TOP)
        );
    }

    // Return bullet array for the weapons
    public Array<GunBullet> getBulletArray() {
        return bullets;
    }

    // Get players for the hud
    public Array<CustomEntity> getPlayerArray() {
        return players;
    }

    // Update the world
    public void step(float delta) {

        // Apply gravity and check players with map
        for (int i = 0; i < players.size; i++) {

            // Get element
            CustomEntity p = players.get(i);

            // Apply forces
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
        for (int i = 0; i < players.size; i++) {

            // Get element
            CustomEntity p = players.get(i);

            // For every bullet
            Iterator<GunBullet> bs = bullets.iterator();

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
            bs = null;

        }

        
        // Move the bullets
        Iterator<GunBullet> bs = bullets.iterator();
        while (bs.hasNext()) {
            GunBullet b = bs.next();

            if (!b.update()) {
                bs.remove();
            }
        }


        // Check throwables with players
        for (int i = 0; i < players.size; i++) {

            // Get element
            CustomEntity p = players.get(i);

            for (int j = 0; j < throwables.size; j++) {

                // Get element
                Throwable t = throwables.get(j);

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
        Iterator<Throwable> ts = throwables.iterator();
        while(ts.hasNext()) {
            Throwable t = ts.next();

            if (!t.update()) {
                ts.remove();
            }
        }

        // Check for supply drop collisions with platforms/players
        Iterator<SupplyDrop> sdIt = supplydrops.iterator();
        while (sdIt.hasNext()) {

            // Get supply drop
            SupplyDrop sd = sdIt.next();
            
            // Move it down
            sd.update();

            // With platforms
            for (int i = 0; i < platforms.size; i++) {

                // Get element
                Platform pl = platforms.get(i);

                if (pl.hBox.isColliding(sd.hBox)) {

                    sd.stopMoving();
                    sd.setY(pl.hBox.getTop());

                }

            }


            // With players
            for (int i = 0; i < players.size; i++) {

                // Get element
                CustomEntity p = players.get(i);

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
        for (int i = 0; i < platforms.size; i++) {

            // Get element
            Platform plat = platforms.get(i);

            // Is the right one
            if (p.touching != null)
                if (!p.touching.equals(plat))
                    continue;

            // Check for collision
            if (p.getFeetHitbox().isColliding(plat.hBox)) {

                // If already touching then ignore
                if (p.touching != null) { continue; }

                // Determine if collision is from above or below
                // temp = new Hitbox(p.getFeetHitbox().x+(p.pBody.getVx() > 0 ? -6 : 6), p.getFeetHitbox().y-20, p.getFeetHitbox().w, p.getFeetHitbox().h);
                temp.updatePos(p.getFeetHitbox().x+(p.pBody.getVx() > 0 ? -6 : 6), p.getFeetHitbox().y-20);

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
        for (int i = 0; i < supplydrops.size; i++) {
            supplydrops.get(i).draw(b);
        }

        // Draw bullets
        for (int i = 0; i < bullets.size; i++) {
            bullets.get(i).draw(b);
        }
        
        // Draw player
        for (int i = 0; i < players.size; i++) {
            players.get(i).draw(b);
        }

        // Draw throwables
        for (int i = 0; i < throwables.size; i++) {
            throwables.get(i).draw(b);
        }

        

        // Debug show platform hitboxes
        /*
        for (Platform p : platforms) {
            b.draw(p.getTexture(), p.x, p.y);
        }
        */

    }

    @Override
    public void dispose() {


        for (CustomEntity p : players) {
            p.dispose();
        }

        SupplyDrop.dispose();

        for (Platform p : platforms) {
            p.dispose();
        }
        
    }

}
