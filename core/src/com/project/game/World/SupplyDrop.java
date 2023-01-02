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
    
    // Spawn probability (higher is less likely)
    public static int SPAWN_CHANCE = 400;

    // For choosing the reward
    public static int NUM_REWARDS = 2;

    // Movement
    Vector2 pos, vel;
    Hitbox hBox;

    // Drawing
    public static Texture supplyTexture;
    private Image supplyImg;
    static {
        supplyTexture = new Texture(Gdx.files.internal("other/supply.png"));
    }
    
    // Constructor
    public SupplyDrop(float x, float y) {

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

}
