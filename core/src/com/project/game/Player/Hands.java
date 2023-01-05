package com.project.game.Player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

public class Hands implements Disposable {
    
    // Config
    PlayerConfig pConfig;

    // Texture
    public Texture handTexture;
    public Image handImage;

    // Position back hand
    private float bx1, by1;
    private float bx2, by2;

    // Position front hand
    private float fx1, fy1;
    private float fx2, fy2;

    // Constructor
    public Hands(PlayerConfig pConfig) {
        this.pConfig = pConfig;

        // Load texture based on config
        handTexture = new Texture(Gdx.files.internal("skins/hand/" + pConfig.playerColorNumber + ".png"));
        handImage = new Image(handTexture);

    }

    // Create position to draw at
    public void setLerpPosFront(float x, float y) {
        fx2 = x;
        fy2 = y;
    }
    public void setLerpPosBack(float x, float y) {
        bx2 = x;
        by2 = y;
    }

    // Lerp to given pos
    public void update() {
        fx1 += (fx2-fx1) * 0.4;
        fy1 += (fy2-fy1) * 0.4;
        bx1 += (bx2-bx1) * 0.4;
        by1 += (by2-by1) * 0.4;
    }

    // Override lerp, set pos
    public void setFrontHandPos(float x, float y) {
        fx2 = fx1 = x;
        fy2 = fy1 = y;
    }
    public void setBackHandPos(float x, float y) {
        bx2 = bx1 = x;
        by2 = by1 = y;
    }

    // Hands at positions
    public void drawFrontHand(Batch sb) {

        // Draw front hand
        handImage.setPosition(fx1 - handImage.getWidth()/2, fy1 - handImage.getHeight()/2);
        handImage.draw(sb, 1);

    }
    public void drawBackHand(Batch sb) {

        // Draw back hand
        handImage.setPosition(bx1 - handImage.getWidth()/2, by1 - handImage.getHeight()/2);
        handImage.draw(sb, 1);

    }

    @Override
    public void dispose() {
        
        if (handTexture != null)
            handTexture.dispose();
        
    }



}
