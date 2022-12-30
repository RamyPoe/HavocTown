package com.project.game.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.Player.Hitbox;

public class GunBullet {
    
    // Movement
    private Vector2 pos;
    private Vector2 speed;
    private float distance;
    private float vx;
    private float strength;
    private boolean flip;

    private float maxTravelDistance;
    private float trailScale;
    private float angle;

    // Drawing
    private static Texture bulletTexture;
    private Image bulletImage;
    static {
        bulletTexture = new Texture( Gdx.files.internal("weapons/bullets/0.png") );
    }

    // Bullet logic
    public Hitbox hBox;


    // Constructor
    public GunBullet(float x, float y, float vx, float maxTravelDistance, float angle, float trailScale, float strength, boolean flip) {

        // Adjust angle for direction
        angle = flip ? angle : 180-angle;

        // Create image
        bulletImage = new Image(bulletTexture);
        bulletImage.setRotation(angle);

        // Create hitbox
        hBox = new Hitbox(
            (flip ? x+bulletImage.getWidth()-20 : x-bulletImage.getWidth()),
            y,
            20,
            6
        );

        // Save fields
        this.maxTravelDistance = maxTravelDistance;
        this.trailScale = trailScale;
        this.vx = vx;
        this.strength = strength;
        this.flip = flip;
        this.angle = angle;

        // Create vectors
        pos = new Vector2(x, y);
        speed = new Vector2(vx, 0);
        speed.setAngleDeg(angle);

    }

    // Get direction
    public boolean getFlip() {
        return flip;
    }

    // For collisions
    public float getForceOverDistance() {

        // -1.936 * [(x-0.5) ^ 2] + 1
        float percent = vx / maxTravelDistance;
        return vx/percent <= 0.5 ? strength : strength * (float) (1.936 * Math.pow(percent-0.5, 2) + 1);

    }

    // Update position, returns false if it should be deleted
    public boolean update() {

        // Travel
        pos.add(speed);
        distance += vx;

        // Check max distance
        if (distance >= maxTravelDistance) {
            return false;
        }

        // Update hitbox
        hBox.updatePos(
            pos.x + (Math.cos(Math.toRadians(angle))*bulletImage.getWidth()) + (flip ? -20 : 0),
            pos.y + (Math.sin(Math.toRadians(angle))*bulletImage.getWidth()) + (flip ? 0 : -bulletImage.getHeight())
        );

        // We are good
        return true;

    }

    // Draw the bullet
    public void draw(Batch sb) {

        // Draw
        bulletImage.setPosition(pos.x, pos.y + (flip ? -1 : 1) * bulletImage.getHeight());
        bulletImage.draw(sb, 1);
        hBox.draw(sb);

    }

}
