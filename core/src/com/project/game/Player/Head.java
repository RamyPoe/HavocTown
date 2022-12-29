package com.project.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;

public class Head {
    
    // Animation Settings (ms)
    private static float ANIMATION_BOB_LENGTH = 2000f;
    
    // Custom offsets for the different hats
    private static int[] hat_offsetsY = new int[]{8, 25, -11};
    private static int[] hat_offsetsX = new int[]{3, 7, 13};

    // Config
    PlayerConfig pConfig;

    // Textures
    Texture headTexture;
    Texture faceTexture;
    Texture hatTexture;

    // Aerial Face movement animation
    private float face_y1, face_y2;


    // Constructor
    public Head(PlayerConfig pConfig) {
        this.pConfig = pConfig;

        // Load textures based on config
        headTexture = new Texture(Gdx.files.internal("skins/head/" + pConfig.playerColorNumber + ".png"));
        if (pConfig.faceSkin != -1)
            faceTexture = new Texture(Gdx.files.internal("skins/face/" + pConfig.faceSkin + ".png"));
        if (pConfig.hatSkin != -1)
            hatTexture = new Texture(Gdx.files.internal("skins/hat/" + pConfig.hatSkin + ".png"));

    }

    // Drawing
    public void draw(Batch sb, CustomEntity p) {

        // Get cars
        float y = p.getY();
        float x = p.getX();

        // Calculate face animation
        if (p.pBody.getVy() > 0) {
            face_y2 = -7;
        } else if (p.pBody.getVy() < 0) {
            face_y2 = 7;
        } else {
            face_y2 = 0;
        }

        // Lerp
        face_y1 += (face_y2-face_y1) * 0.1;

        // Offset from body
        y += CustomEntity.P_HEIGHT - 42 + animationBob();
        x += CustomEntity.P_WIDTH/2 + (p.flip ? 1 : -1) * -10;

        // Draw head
        sb.draw(headTexture, x, y, headTexture.getWidth() * (p.flip ? 1 : -1), headTexture.getHeight());

        // Draw hat
        y += 20 + hat_offsetsY[pConfig.hatSkin]; x -= (p.flip ? 1 : -1) * (hatTexture.getWidth()/2 - 14 - hat_offsetsX[pConfig.hatSkin]);
        if (hatTexture != null)
        sb.draw(hatTexture, x, y, hatTexture.getWidth() * (p.flip ? 1 : -1), hatTexture.getHeight());

        // Draw face
        y += face_y1 - hat_offsetsY[pConfig.hatSkin]; x += (p.flip ? 1 : -1) * 36;
        if (faceTexture != null)
            sb.draw(faceTexture, x, y, faceTexture.getWidth() * (p.flip ? 1 : -1), faceTexture.getHeight());

    }

    // Get animation offset
    public float animationBob() {

        // Function input
        float x = (System.nanoTime()/1_000_000) % ANIMATION_BOB_LENGTH;

        // Run through function
        float y = animationFunction(
            x / ANIMATION_BOB_LENGTH
        );
        y = y*6 - 1;

        // Return the bob offset
        return y;

    }

    // Function that dictates animation
    private float animationFunction(float x) {

        // y = -4(x)(x-1)
        return -4 * (x) * (x-1);

    }

}
