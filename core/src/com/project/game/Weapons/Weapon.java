package com.project.game.Weapons;

import java.util.ArrayList;

import javax.swing.text.WrappedPlainView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;

public abstract class Weapon {
    
    // Idle animation for all weapons
    protected int IDLE_TIME = 2000;
    protected int IDLE_ANIMATION_TIME = 1000;
    protected long time_last_shot = 0;
    protected float idleRot = 0;

    // Weapon logic
    protected boolean disposable;
    protected int shootTime;
    protected int weight, recoil;
    protected int gun_length;
    protected int bulletSpeed;
    protected int reloadTime;
    protected int max_ammo;
    protected int ammo;
    protected float strength;
    protected int weaponNumber;

    protected boolean lightNozzle = false;

    // Movement
    protected float x2, y2, r2, x1, y1, r1;
    public boolean flip;

    // Drawing
    protected static Texture[] nozzleFlash;
    protected static Texture[] weaponTextures;
    protected Image wpnImage;
    
    protected int originX, originY;
    protected static int[] weaponOffsetsX;
    protected static int[] weaponOffsetsY;

    // Load textures
    static {
        nozzleFlash = new Texture[2];
        nozzleFlash[0] = new Texture(Gdx.files.internal("weapons/nozzle/0.png"));
        nozzleFlash[1] = new Texture(Gdx.files.internal("weapons/nozzle/1.png"));
        
        weaponTextures = new Texture[1];
        weaponTextures[0] = new Texture(Gdx.files.internal("weapons/guns/0.png"));

        weaponOffsetsX = new int[]{0};
        weaponOffsetsY = new int[]{20};
    }

    // Constructor
    public Weapon(int weaponNumber, int shootTime, int weight, int recoil, int gun_length, int bulletSpeed,
                  boolean disposable, int max_ammo, int reloadTime, float strength, CustomEntity p) {
                    
        this.wpnImage = new Image(weaponTextures[weaponNumber]);
        this.shootTime = shootTime;
        this.weight = weight;
        this.recoil = recoil;
        this.gun_length = gun_length;
        this.bulletSpeed = bulletSpeed;
        this.disposable = disposable;
        this.max_ammo = max_ammo;
        this.ammo = max_ammo;
        this.reloadTime = reloadTime;
        this.strength = strength;
        this.weaponNumber = weaponNumber;

        // Starting positions
        this.x2 = this.x1 = (int) p.getX();
        this.y2 = this.y1 = (int) p.getY();
        r2 = r1 = 0;

    }

    // Spawn bullet(s)
    public void shoot(ArrayList<GunBullet> bullets, CustomEntity p) {
        lightNozzle = true;
    }

    // Setter
    public void initHandle(int originX, int originY, float idleRot) {
        this.wpnImage.setOrigin(originX, originY);
        this.originX = originX;
        this.originY = originY;
        this.idleRot = idleRot;
    }

    // Getters
    public float getWeight() { return weight; }

    // For drawing
    public void draw(Batch sb, CustomEntity p) {

        // Set positions
        if (MainGame.getTimeMs()-time_last_shot > IDLE_TIME) {

            // In Animation
            x2 = (p.flip ? 1 : -1) * (weaponOffsetsX[weaponNumber]);
            y2 = 20;
            r2 = idleRot;

        }
        
        // Shooting position
        else {

            // Shooting positions
            y2 = 20 + weaponOffsetsY[weaponNumber];
            x2 = (p.flip ? 1 : -1) * (weaponOffsetsX[weaponNumber]);
            r2 = 0;
            
            // Include recoil
            float timeDif = MainGame.getTimeMs()-time_last_shot;
            if (timeDif < shootTime) {

                x2 += spikeLerp(timeDif/(shootTime)) * -20 * (flip ? 1 : -1);
                r2 += spikeLerp(timeDif/(shootTime)) * 15;

            }

            x1 = x2;
            y1 = y2;
            r1 = r2;

        }

        // Lerp
        x1 += (x2-x1) * 0.2;
        y1 += (y2-y1) * 0.2;
        r1 += (r2-r1) * 0.2;


        // Draw at the positions
        wpnImage.setOriginX(p.flip ? originX : 0);
        wpnImage.setScaleX(p.flip ? 1 : -1);
        wpnImage.setPosition(p.getX()+CustomEntity.P_WIDTH/2 + x1, p.getY() + y1);
        wpnImage.setRotation(p.flip ? r1 : 360-r1);
        wpnImage.draw(sb, 1);

        // Draw nozzle
        if (lightNozzle) {
            
            int i = (int) Math.round(Math.random());
            sb.draw(nozzleFlash[i], wpnImage.getX() + (flip ? gun_length : -gun_length), wpnImage.getY()+30-nozzleFlash[i].getHeight()/2, (flip?1:-1) * nozzleFlash[i].getWidth(), nozzleFlash[i].getHeight());

            lightNozzle = false;
        }


    }

    // Lerp with spike for recoil
    public static float spikeLerp(float t) {

        if (t <= 0.5)
            return (float) Math.pow(t/0.5, 3);
        return (float) Math.pow((1.0-t)/0.5, 3);

    }

}
