package com.project.game.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.project.game.MainGame;

public class Hitbox {
    
    // From bottom-left
    public double x, y, w, h;

    // Texture
    Texture t;

    // Constructor
    public Hitbox(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        t = MainGame.createTexture((int) w, (int) h, Color.CHARTREUSE);
    }

    // Collision
    public boolean isColliding(Hitbox b) {
        
        // Collision check
        if (
            x < b.x + b.w &&
            x + w > b.x   &&
            y + h > b.y   &&
            y < b.y + b.h
        ) {
            
            return true;
        }
        return false;

    }

    // Getters
    public double getTop()    { return y+h; }
    public double getBottom() { return y;   }
    public double getRight()  { return x+w; }
    public double getLeft()   { return x;   }

    // Updating
    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Debug 
    public void draw(Batch sb) {

        sb.draw(t, (float) x, (float) y);

    }

}
