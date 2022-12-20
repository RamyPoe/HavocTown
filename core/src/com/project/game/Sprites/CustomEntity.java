package com.project.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.project.game.MainGame;

public class CustomEntity extends Sprite {
    
    public PhysicsBody pBody;

    public CustomEntity() {

        super(
            new Texture(Gdx.files.internal("skins/blue.jpg"))
        );

        // Enable physics body
        pBody = new PhysicsBody(MainGame.V_WIDTH/2, MainGame.V_HEIGHT/2);

        this.setSize(100, 100);

    }

    // Set positions
    public void update() {

        // Update physics body
        pBody.update();

        // Get position from physics body
        this.setPosition(
            (float) pBody.getDx(),
            (float) pBody.getDy()
        );

        System.out.println("DX: " + pBody.getDx() + "     |   DY: " + pBody.getDy());

    }
    

}
