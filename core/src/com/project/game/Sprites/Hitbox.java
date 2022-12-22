package com.project.game.Sprites;

public class Hitbox {
    
    // From bottom-left
    public double x, y, w, h;

    // Constructor
    public Hitbox(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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

}
