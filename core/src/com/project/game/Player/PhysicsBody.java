package com.project.game.Player;

public class PhysicsBody {
    
    // Constants
    public double DRAG_CONSTANT;
    public double DRAG_DEGREE;
    public double ACCELERATION_FACTOR;

    // Position
    private double dx, dy;
    private int max_dx, max_dy;
    
    // Speed
    private double vx, vy;
    private int max_vx, max_vy;
    
    // Start with inital position
    public PhysicsBody(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

        // Default Values
        DRAG_CONSTANT = 1;
        DRAG_DEGREE = 1;
        ACCELERATION_FACTOR = 1;
    }

    // Change velocity
    public void applyForceY(double a) { vy += a; }
    public void applyForceX(double a) { vx += a; }

    // Apply friction to give gradual slow down
    public void applyFrictionX() {

        vx *= DRAG_CONSTANT;

    }
    
    // Update position
    public void update() {

        // Respect max speed bounds
        if (vx > max_vx) { vx = max_vx; }
        else if (vx < -max_vx) { vx = -max_vx; }
    

        // Displace with velocities
        dx += vx;
        dy += vy;
    }
    



    //=============================
    //     GETTERS && SETTERS
    //=============================

    public double getDx() { return this.dx; }
    public void setDx(double dx) { this.dx = dx; }

    public double getDy() { return this.dy; }
    public void setDy(double dy) { this.dy = dy; }

    public int getMax_dx() { return this.max_dx; }
    public void setMax_dx(int max_dx) { this.max_dx = max_dx; }

    public int getMax_dy() { return this.max_dy; }
    public void setMax_dy(int max_dy) { this.max_dy = max_dy; }

    public double getVx() { return this.vx; }
    public void setVx(double vx) { this.vx = vx; }

    public double getVy() { return this.vy; }
    public void setVy(double vy) { this.vy = vy; }

    public int getMax_vx() { return this.max_vx; }
    public void setMax_vx(int max_vx) { this.max_vx = max_vx; }

    public int getMax_vy() { return this.max_vy; }
    public void setMax_vy(int max_vy) { this.max_vy = max_vy; }
    
}
