package com.project.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.project.game.MainGame;

public class CustomEntity extends Sprite {
    
    public PhysicsBody pBody;

    public CustomEntity() {

        // Initialize sprite
        super(
            new Texture(Gdx.files.internal("skins/blue.jpg"))
        );

        // Enable physics body
        pBody = new PhysicsBody(MainGame.V_WIDTH/2, MainGame.V_HEIGHT/2);

        // Configure physics
        pBody.setMax_vx(10);
        pBody.setMax_vy(10);

        pBody.DRAG_CONSTANT = 0.2;
        pBody.DRAG_DEGREE = 0.8;
        pBody.ACCELERATION_FACTOR = 3.0;


    }

    // Apply movements
    public void moveLeft(float dt) {
        pBody.applyForceX(-10 * pBody.ACCELERATION_FACTOR * dt);
    }
    public void moveRight(float dt) {
        pBody.applyForceX(10 * pBody.ACCELERATION_FACTOR * dt);
    }

    // Set positions
    public void update(float dt) {

        // Update physics body
        pBody.applyFrictionX(dt);
        pBody.update(dt);

        // Get position from physics body
        this.setPosition(
            (float) pBody.getDx(),
            (float) pBody.getDy()
        );

        // System.out.println("VX: " + pBody.getVx());

    }
    

}
