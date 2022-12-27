package com.project.game.Weapons;

import java.util.ArrayList;

import javax.swing.text.WrappedPlainView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.Player.CustomEntity;

public abstract class Weapon {
    
    // Weapon logic
    protected boolean disposable;
    protected int shootTime;
    protected long time_last_shot = 0;
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
    protected int x2, y2, x1, y1;
    public boolean flip;

    // Drawing
    protected static Texture[] nozzleFlash;
    protected Image wpnImage;

    // Load nozzle flash textures
    static {
        nozzleFlash = new Texture[2];
        nozzleFlash[0] = new Texture(Gdx.files.internal("weapons/nozzle/0.png"));
        nozzleFlash[1] = new Texture(Gdx.files.internal("weapons/nozzle/1.png"));
    }

    // Constructor
    public Weapon(Texture wpnTexture, int shootTime, int weight, int recoil, int gun_length, int bulletSpeed,
                  boolean disposable, int max_ammo, int reloadTime, float strength) {
                    
        this.wpnImage = new Image(wpnTexture);
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
    }

    // Spawn bullet(s)
    public abstract void shoot(ArrayList<GunBullet> bullets, CustomEntity p);

    // Getters
    public float getWeight() { return weight; }

}
