/*
* The body element of the CustomEntity class. It
* holds textures for shirt as well.
* 
* @author  Rameen Popal
* @since   2023-01-31
*/


package com.project.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public class Body implements Disposable {

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
    public void draw(Batch sb, CustomEntity p) {

        // Get vars
        float y = p.getY();
        float x = p.getX();

        // Room to draw feet
        y += 5;

        // Draw
        sb.draw(bodyTexture, x, y);
        if (shirtTexture != null)
            sb.draw(shirtTexture, p.flip ? x : x+shirtTexture.getWidth(), y, shirtTexture.getWidth() * (p.flip ? 1 : -1), shirtTexture.getHeight());

    }

    @Override
    public void dispose() {

        if (bodyTexture != null)
            bodyTexture.dispose();
        if (shirtTexture != null)
            shirtTexture.dispose();
        
    }

}
