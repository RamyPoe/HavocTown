package com.project.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Feet {
    
    // Animation speed (ms)
    private static float ANIMATION_LENGTH = 400;

    // Config
    PlayerConfig pConfig;

    // Texture
    Texture footTexture;
    Image footImage;

    // Position back foot
    private float bx1, by1, br1;
    private float bx2, by2, br2;

    // Position front foot
    private float fx1, fy1, fr1;
    private float fx2, fy2, fr2;

    public Feet(PlayerConfig pConfig) {
        this.pConfig = pConfig;

        // Load texture based on config
        footTexture = new Texture(Gdx.files.internal("skins/feet/" + pConfig.playerColorNumber + ".png"));
        footImage = new Image(footTexture);
    }

    // Create positions to be drawn
    public void updatePos(double vx, double vy, boolean grounded, float x, float y, boolean flip, boolean applying_force) {

        // Offset
        float a = 0f;
        fr2 = 0f;
        br2 = 0f;

        // Walking on the ground
        if (grounded && (Math.abs(vx) > 2 || applying_force)) {

            // Time used for animation functions
            float t = (System.nanoTime()/1_000_000) % ANIMATION_LENGTH;

            // Animation for the horizontal movement
            t /= ANIMATION_LENGTH;
            a = animationFunction(t) * 23;

            // Animation for the rotation while walking
            fr2 = rotationWalkAnimation(t) * 30;
            t+=0.5; if (t > 1) { t -= 1;}
            br2 = rotationWalkAnimation(t) * 30;
        } 
        
        // In the air
        else if (!grounded) {
            
            // Jumping up, point toes down
            if (vy > 0) {
                br2 = -60;
                fr2 = -60;
            } 
            
            // Falling down, point toes up
            else {
                br2 = 10;
                fr2 = 10;
            }

        }

        // Apply calculated offset
        bx2 = x + CustomEntity.P_WIDTH/2 + (flip ? 1 : -1) * (10 - a);
        by2 = 0 + y;

        // Apply calculated offset
        fx2 = x + CustomEntity.P_WIDTH/2 + (flip ? 1 : -1) * (-28 + a);
        fy2 = -3 + y;

        // For drawing after
        footImage.setScaleX((flip ? 1 : -1));

        // Lerp
        bx1 += (bx2-bx1) * 0.7;
        by1 = by2;
        br1 += (br2-br1) * (grounded ? 0.7 : 0.3);

        fx1 += (fx2-fx1) * 0.7;
        fy1 = fy2;
        fr1 += (fr2-fr1) * (grounded ? 0.7 : 0.3);

    }
    
    // Draw back foot
    public void drawBack(Batch sb, boolean flip) {

        // Edit Sprite
        footImage.setPosition(bx1, by1);
        footImage.setRotation(br1 * (flip ? 1 : -1));


        // Draw foot
        footImage.draw(sb, 1);

    }
    
    // Draw front foot
    public void drawFront(Batch sb, boolean flip) {
    
        // Edit Sprite
        footImage.setPosition(fx1, fy1);
        footImage.setRotation(fr1 * (flip ? 1 : -1));
        
        // Draw foot
        footImage.draw(sb, 1);

    }

    // Function to map animation
    public float animationFunction(float x) {

        // y = -2 * | x- 0.5 | + 1
        return (float) (-2 * Math.abs(x-0.5) + 1);

    }

    // Rotate of foot during anumation
    public float rotationWalkAnimation(float x) {

        if (x < 0.5) {

            return (float) (-2 * Math.abs(x-0.5) + 1);
            
        } else if (x < 0.71) {

            return (float) (-4.6 * Math.abs(x-0.5) + 1);
            
        } else {

            return 0;

        }

    }



}
