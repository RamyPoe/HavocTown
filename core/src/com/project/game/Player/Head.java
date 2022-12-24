package com.project.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Head {
    
    // Custom offsets for the different hats
    public static int[] hat_offsetsY = new int[]{8, 25, -11};
    public static int[] hat_offsetsX = new int[]{3, 7, 13};

    // Config
    PlayerConfig pConfig;

    // Textures
    Texture headTexture;
    Texture faceTexture;
    Texture hatTexture;

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
    public void draw(Batch sb, float x, float y, boolean flip) {

        // Offset from body
        y += CustomEntity.P_HEIGHT - 43;
        x += CustomEntity.P_WIDTH/2 + (flip ? 1 : -1) * -10;

        // Draw head
        sb.draw(headTexture, x, y, headTexture.getWidth() * (flip ? 1 : -1), headTexture.getHeight());

        // Draw face
        y += 20; x += (flip ? 1 : -1) * 14;
        if (faceTexture != null)
            sb.draw(faceTexture, x, y, faceTexture.getWidth() * (flip ? 1 : -1), faceTexture.getHeight());

        // Draw hat
        y += hat_offsetsY[pConfig.hatSkin]; x -= (flip ? 1 : -1) * (hatTexture.getWidth()/2 - hat_offsetsX[pConfig.hatSkin]);
        if (hatTexture != null)
            sb.draw(hatTexture, x, y, hatTexture.getWidth() * (flip ? 1 : -1), hatTexture.getHeight());

    }

}
