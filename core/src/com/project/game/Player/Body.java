package com.project.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Body {

    // Config
    PlayerConfig pConfig;

    // Textures
    Texture bodyTexture = null;
    Texture shirtTexture = null;

    public Body(PlayerConfig pConfig) {
        this.pConfig = pConfig;

        // Load textures based on config
        bodyTexture = new Texture(Gdx.files.internal("skins/body/" + pConfig.playerColorNumber + ".png"));
        if (pConfig.shirtSkin != -1)
            shirtTexture = new Texture(Gdx.files.internal("skins/shirt/" + pConfig.shirtSkin + ".png"));

    }

    // Drawing
    public void draw(Batch sb, float x, float y, boolean flip) {

        // Room to draw feet
        y += 5;

        // Draw
        sb.draw(bodyTexture, x, y);
        if (shirtTexture != null)
            sb.draw(shirtTexture, flip ? x : x+shirtTexture.getWidth(), y, shirtTexture.getWidth() * (flip ? 1 : -1), shirtTexture.getHeight());

    }

}
