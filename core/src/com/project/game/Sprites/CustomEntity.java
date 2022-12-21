package com.project.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.project.game.MainGame;

public class CustomEntity extends Sprite {
    
    // Player parts

    // For movement
    public PhysicsBody pBody;
    public Hitbox hBox;

    // Constructor
    public CustomEntity() {
        
        // Initialize sprite
        super(
            new Texture(Gdx.files.internal("skins/blue.jpg"))
        );

        // Set size
        this.setSize(50, 80);

        // Enable physics body
        pBody = new PhysicsBody(MainGame.V_WIDTH/2, MainGame.V_HEIGHT/2);

        // Configure physics
        pBody.setMax_vx(3);
        pBody.setMax_vy(10);

        pBody.DRAG_CONSTANT = 0.2;
        pBody.DRAG_DEGREE = 0.8;
        pBody.ACCELERATION_FACTOR = 3.0;

        // Collision checks
        hBox = new Hitbox(
            pBody.getDx(),
            pBody.getDy(),
            50d,
            80d
        );


    }
    

    // Apply movements
    public void moveLeft() {
        pBody.applyForceX(-10 * pBody.ACCELERATION_FACTOR);
    }
    public void moveRight() {
        pBody.applyForceX(10 * pBody.ACCELERATION_FACTOR);
    }

    // Changing position
    public void setPosX(double x) {
        pBody.setDx(x);
        this.setX( (float) x);
    }
    public void setPosY(double y) {
        pBody.setDy(y);
        this.setY( (float) y);
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

        // Get position from physics body
        this.setPosition(
            (float) pBody.getDx(),
            (float) pBody.getDy()
        );

        // System.out.println("VX: " + pBody.getVx());

    }
    

}
