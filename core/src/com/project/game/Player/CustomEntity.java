/*
* Class that represents a game player. Movement
* is affected by physics elements and computed
* animations.
* 
* @author  Rameen Popal
* @since   2023-01-31
*/

package com.project.game.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.project.game.MainGame;
import com.project.game.Weapons.GunBullet;
import com.project.game.Weapons.GunLibrary;
import com.project.game.Weapons.Weapon;
import com.project.game.World.Platform;

public class CustomEntity implements Disposable {
    
    // Player constants
    public static float P_WIDTH = 65;
    public static float P_HEIGHT = 102;
    public static short MAX_JUMPS = 2;

    // Player parts
    Feet p_feet;
    Body p_body;
    Head p_head;
    Hands p_hands;

    // Player weapon
    Weapon weapon;

    // For movement/collision
    public PhysicsBody pBody;
    public Hitbox hBox;

    // Player logic
    public boolean wants_to_pass_through = false;
    public short jumps = 0;
    public boolean grounded = false;
    public boolean applying_force = false;
    public Platform touching;
    public int lives;

    // True facing right, False facing left
    public boolean flip;


    // Constructor
    public CustomEntity(float x, float y, PlayerConfig config) {
        
        // Initialize character parts
        p_head = new Head(config);
        p_feet = new Feet(config);
        p_body = new Body(config);
        p_hands = new Hands(config);

        // Enable physics body
        pBody = new PhysicsBody(MainGame.V_WIDTH/2, MainGame.V_HEIGHT/2);

        // Configure physics
        pBody.setMax_vx(5);
        pBody.setMax_vy(4);
        pBody.setDx(x);
        pBody.setDy(y);

        pBody.DRAG_CONSTANT = 0.95;
        pBody.DRAG_DEGREE = 0.8;
        pBody.ACCELERATION_FACTOR = 0.2;

        // Collision checks
        hBox = new Hitbox(
            pBody.getDx(),
            pBody.getDy(),
            P_WIDTH,
            P_HEIGHT
        );

        // Reset for defualt config
        this.reset();

    }
    
    // Try to shoot the gun and apply recoil
    public void shoot(Array<GunBullet> bullets) {
        weapon.shoot(bullets, this);
    }

    // When assigning a weapon
    public void giveWeapon(Weapon w) {
        this.weapon = w;
        this.pBody.setMax_vx(
            5.5 - w.getWeight()*0.3
        );
        w.setHandsObject(p_hands);
    }

    // When hit by bullet
    public void bulletHit(GunBullet b) {
        if (b.getFlip()) {
            pBody.applyForceX(b.getForceOverDistance());
        } else {
            pBody.applyForceX(-b.getForceOverDistance());
        }
    }

    // Reset player for spawning
    public void reset() {

        // Means we died
        lives--;

        // Default weapon
        this.giveWeapon(GunLibrary.pistol(this));

        // Reset physics
        this.pBody.setVx(0);
        this.pBody.setVy(0);

        // Reset body part position to avoid lerp
        this.p_hands.setFrontHandPos(getX(), getY());
        this.p_hands.setBackHandPos(getX(), getY());

    }

    // Get the player config
    public PlayerConfig getPlayerConfig() {
        return p_head.pConfig;
    }

    // Get the ammo for the current weapon
    public int getAmmoCount() {
        return weapon.getAmmoCount();
    }

    // Drawing with animations
    public void draw(Batch sb) {

        // For the feet
        p_feet.updatePos(this);

        // Update Gun/Hand position
        weapon.update(p_feet, this);

        // Upadte hand position
        p_hands.update();

        // Draw the back hand
        p_hands.drawBackHand(sb);

        // Back foot
        p_feet.drawBack(sb, flip);
        
        // Body w/ shirt
        p_body.draw(sb, this);
        
        // Head w/ face & hat
        p_head.draw(sb, this);

        // Front foot
        p_feet.drawFront(sb, flip);

        // Draw the weapon
        weapon.draw(sb,this);

        // Draw hands
        p_hands.drawFrontHand(sb);

    }

    // Update position when in model mode
    public void updatePosModel() {
        
        // For the feet
        p_feet.updatePos(this);

        // Update Gun/Hand position
        weapon.update(p_feet, this);

        // Upadte hand position
        p_hands.update();

    }

    // For pass through platforms
    public void passThroughPlatform() {
        wants_to_pass_through = true;
    }

    // For platform collisions
    public Hitbox getFeetHitbox() {
        return p_feet.hBox;
    }


    // Apply movements
    public void moveLeft() {
        double f = -1 * pBody.ACCELERATION_FACTOR * (grounded ? 1 : 0.6);
        flip = false; weapon.flip = flip;

        if (Math.abs(f+pBody.getVx()) > pBody.getMax_vx()) {
            return;
        }
        pBody.applyForceX(f);

        applying_force = true;
    }
    public void moveRight() {
        double f = 1 * pBody.ACCELERATION_FACTOR * (grounded ? 1 : 0.6);
        flip = true; weapon.flip = flip;

        if (Math.abs(f+pBody.getVx()) > pBody.getMax_vx()) {
            return;
        }
        pBody.applyForceX(f);

        applying_force = true;
    }

    // Jumping
    public void jump() {

        if (jumps <= 0) { return; }

        this.pBody.setVy(0);
        this.pBody.applyForceY(15);
        this.jumps--;

    }

    // Slow down when player isn't accelerating
    public void applyFrictionX() {
        if (applying_force) {
            if (Math.abs(pBody.getVx()) > pBody.getMax_vx()) {
                this.pBody.applyFrictionX();
            }
        } else {
            this.pBody.applyFrictionX();
        }
    }

    // Changing position
    public void setPosX(double x) {
        pBody.setDx(x);
    }
    public void setPosY(double y) {
        pBody.setDy(y);
    }

    // Apply gravity
    public void applyGravity() {
        pBody.applyForceY(-0.5);
    }

    // Set positions (called once per frame)
    public void update() {

        // Update physics body
        pBody.update();

        // Update collision bodies
        hBox.updatePos(pBody.getDx(), pBody.getDy());
        p_feet.updateHbox(this);

    }

    // Getters for drawing
    public float getX() { return (float) pBody.getDx(); }
    public float getY() { return (float) pBody.getDy(); }

    @Override
    public void dispose() {

        p_body.dispose();
        p_feet.dispose();
        p_hands.dispose();
        p_head.dispose();

        Weapon.dispose();

    }
    

}
