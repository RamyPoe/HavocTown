package com.project.game.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.project.game.Sprites.Hitbox;

public class Platform extends Sprite {
    
    // From bottom-left
    float x, y, w, h;

    // For collisions
    Hitbox hBox;

    // Constructor
    public Platform(float x, float y, float w, float h, boolean passable) {

        // Super
        super(
            createTexture((int)w, (int)h, Color.RED)
        );

        // Store
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        // Create hitbox
        hBox = new Hitbox(x, y, w, h);

        // Set sprite dimensions
        this.setSize(w, h);

    }

    // So we can draw it
    private static Texture createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

}
