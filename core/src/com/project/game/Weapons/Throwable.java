package com.project.game.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.Player.Hitbox;

public class Throwable {
    
    // Throw constants
    public static final float THROW_FORCE = 8;
    private static final float THROW_ANGLE = 45;
    private static final float THROW_SPEED = 8;
    private static final float THROW_GRAVITY = 0.3f;
    private static final float OBJECT_WIDTH = 45;
    private static final float OBJECT_HEIGHT = 45;
    private static final float ROTATION_SPEED = 7;


    // For collisions/position
    public Hitbox hBox;
    private Vector2 pos, vel;
    private Vector2 gravityVector;
    public boolean flip;
    public boolean collided = false;

    // For drawing
    private float angle;
    private Image img;

    // Constructor
    public Throwable(float x, float y, Texture t, boolean flip) {

        // Hitbox to check collisions
        hBox = new Hitbox(x + (flip?1:-1) * (t.getWidth()/2 - OBJECT_WIDTH/2), y + t.getHeight()/2 - OBJECT_HEIGHT/2, OBJECT_WIDTH, OBJECT_HEIGHT);

        // Vectors for movement/position
        x -= flip ? 0 : t.getWidth();
        pos = new Vector2(x, y);
        vel = new Vector2(THROW_SPEED, THROW_SPEED);
        vel.setAngleDeg(flip ? THROW_ANGLE : 180-THROW_ANGLE);
        gravityVector = new Vector2(0, -THROW_GRAVITY);

        // Images for drawing
        this.flip = flip;
        img = new Image(t);
        angle = flip ? 0 : 0;

        img.setOrigin(img.getWidth()/2, img.getHeight()/2);
        img.setPosition(pos.x, pos.y);
        img.setScaleX(flip ? 1 : -1);
    }

    // Apply gravity to velocity, return false if should be deleted
    public boolean update() {

        // Update position
        pos.add(vel);
        vel.add(gravityVector);

        // Update hitbox
        hBox.updatePos(pos.x + img.getWidth()/2 - OBJECT_WIDTH/2, pos.y + img.getHeight()/2 - OBJECT_HEIGHT/2);

        // Update image position
        img.setPosition(pos.x, pos.y);
        angle += (flip ? 1 : -1) * ROTATION_SPEED;
        img.setRotation(angle);

        // Delete if out of bounds
        if (pos.x > 2000 || pos.x < -2000 || pos.y < -1000) {
            return false;
        }
        return true;

    }

    // Collided so stop vel_x and flag
    public void setCollided() {
        vel.set(0, vel.y);
        collided = true;
    }

    // Get velocity
    public Vector2 getVel() { return vel; }

    // Draw at current position
    public void draw(Batch sb) {

        // Draw the image
        img.draw(sb, 1);
        hBox.draw(sb);

    }


}
