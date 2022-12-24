package com.project.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.project.game.MainGame;

public class CustomEntity {
    
    // Player constants
    public static float P_WIDTH = 65;
    public static float P_HEIGHT = 102;
    public static short MAX_JUMPS = 999;

    // Player parts
    Feet p_feet;
    Body p_body;
    Head p_head;

    // For movement/collision
    public PhysicsBody pBody;
    public Hitbox hBox;

    // Player logic
    public boolean wants_to_pass_through = false;
    public short jumps = 0;

    // True facing right, False facing left
    public boolean flip;


    // Constructor
    public CustomEntity(float x, float y, PlayerConfig config) {
        
        // Initialize character parts
        p_feet = new Feet(config);
        p_body = new Body(config);
        p_head = new Head(config);
        

        // Enable physics body
        pBody = new PhysicsBody(MainGame.V_WIDTH/2, MainGame.V_HEIGHT/2);

        // Configure physics
        pBody.setMax_vx(3);
        pBody.setMax_vy(4);
        pBody.setDx(x);
        pBody.setDy(y);

        pBody.DRAG_CONSTANT = 0.2;
        pBody.DRAG_DEGREE = 0.8;
        pBody.ACCELERATION_FACTOR = 3.0;

        // Collision checks
        hBox = new Hitbox(
            pBody.getDx(),
            pBody.getDy(),
            P_WIDTH,
            P_HEIGHT
        );


    }
    


    // Drawing with animations
    public void draw(Batch sb) {

        // Body w/ shirt
        p_body.draw(sb, getX(), getY(), flip);

        // Head w/ face & hat
        p_head.draw(sb, getX(), getY(), flip);


    }


    // For pass through platforms
    public void passThroughPlatform() {
        wants_to_pass_through = true;
    }


    // Apply movements
    public void moveLeft() {
        pBody.applyForceX(-10 * pBody.ACCELERATION_FACTOR);
        flip = false;
    }
    public void moveRight() {
        pBody.applyForceX(10 * pBody.ACCELERATION_FACTOR);
        flip = true;
    }

    // Changing position
    public void setPosX(double x) {
        pBody.setDx(x);
    }
    public void setPosY(double y) {
        pBody.setDy(y);
    }

    // Apply gravity
    public void applyGravity() {
        pBody.applyForceY(-0.2);
    }

    // Set positions
    public void update(float dt) {

        // Update physics body
        pBody.applyFrictionX();
        pBody.update(dt);

        // Update collisions body
        hBox.updatePos(pBody.getDx(), pBody.getDy());

    }

    // Getters for drawing
    public float getX() { return (float) pBody.getDx(); }
    public float getY() { return (float) pBody.getDy(); }
    

}
