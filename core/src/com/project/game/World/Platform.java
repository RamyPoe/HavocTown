package com.project.game.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.project.game.MainGame;
import com.project.game.Player.Hitbox;

public class Platform extends Sprite implements Disposable {
    
    
    // From bottom-left
    float x, y, w, h;

    // For collisions
    Hitbox hBox;
    boolean passable;

    // Constructor
    public Platform(float x, float y, float w, float h, boolean passable) {

        // Super
        super(
            MainGame.createTexture((int)w, (int)h, Color.RED)
        );
        this.setSize(w, h);

        // Store
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.passable = passable;

        // Create hitbox
        hBox = new Hitbox(x, y, w, h);


    }

    @Override
    public void dispose() {
        this.getTexture().dispose();
    }

}
