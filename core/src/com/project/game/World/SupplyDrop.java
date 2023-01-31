/*
* A supply drop gets spawned randomly and
* can assign the respective player a
* new and more powerful weapon randomly.
* 
* @author  Rameen Popal
* @since   2023-01-31
*/

package com.project.game.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.Hitbox;
import com.project.game.Weapons.GunLibrary;
import com.project.game.Weapons.Weapon;

public class SupplyDrop {
    
    // For calculating center
    public static final int TEXTURE_WIDTH = 70;

    // Spawn probability (higher is less likely)
    public static final int SPAWN_CHANCE = 400;

    // For choosing the reward
    public static final int NUM_REWARDS = 2;

    // Movement
    Vector2 pos, vel;
    Hitbox hBox;

    // Drawing
    private static Texture supplyTexture;
    private Image supplyImg;
    
    // Constructor
    public SupplyDrop(float x, float y) {

        // Load texture if haven't
        if (supplyTexture == null)
            supplyTexture = new Texture(Gdx.files.internal("other/supply.png"));

        // Create image
        supplyImg = new Image(supplyTexture);

        // Define movement speed
        pos = new Vector2(x, y);
        vel = new Vector2(0, -5);

        // Create hitbox
        hBox = new Hitbox(x, y, supplyImg.getWidth(), supplyImg.getHeight());

    }


    // Move and update hitbox
    public void update() {

        // Move based on velocity
        pos.add(vel);

        // Update hitbox
        hBox.updatePos(pos.x, pos.y);

    }

    // Draw
    public void draw(Batch sb) {

        // Configure image and draw
        supplyImg.setPosition(pos.x, pos.y);
        supplyImg.draw(sb, 1);

    }

    // Set vel to zero
    public void stopMoving() {
        this.vel = new Vector2(0, 0);
    }

    // Set the Y coordinate
    public void setY(double y) {
        pos.set(pos.x, (float) y);
    }


    // Get the weapon when making contact
    public static Weapon getWeaponReward(CustomEntity p) {

        // Generate random number
        int random = (int) Math.round ( Math.random() * (NUM_REWARDS-1) );

        // Give weapon given number
        switch (random) {

            case 0:
                return GunLibrary.ak47(p);
            case 1:
                return GunLibrary.ks23(p);

        }

        // In case
        return null;

    }


    public static void dispose() {
        if (supplyTexture != null) {
            supplyTexture.dispose();
            supplyTexture = null;    
        }
    }

    

}
