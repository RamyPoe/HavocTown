package com.project.game.Weapons;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.Feet;
import com.project.game.Player.Hands;
import com.project.game.World.GameWorld;

public abstract class Weapon {
    
    // For creating throwables
    public static GameWorld gameWorld;
    private boolean madeThrowable = false;

    // Weapon will be responsible for drawing hands
    Hands hands;

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
    protected int reloadTimer;
    protected boolean reloading = false;
    protected int max_ammo;
    protected int ammo;
    protected float strength;
    protected int weaponNumber;

    protected boolean lightNozzle = false;

    // Movement
    protected float x2, y2, r2, z2, x1, y1, r1, z1;
    public boolean flip;

    // Drawing
    protected static Texture[] nozzleFlash;
    protected static Texture[] weaponTextures;
    protected Image wpnImage;
    protected int originX, originY;

    protected int backHandPosX;
    protected int backHandPosY;
    protected float recoilRot;

    // Offsets for images
    protected int weaponStartX;
    protected int weaponEndX;
    protected int weaponStartY;
    protected int weaponEndY;

    // Load textures
    static {
        nozzleFlash = new Texture[2];
        nozzleFlash[0] = new Texture(Gdx.files.internal("weapons/nozzle/0.png"));
        nozzleFlash[1] = new Texture(Gdx.files.internal("weapons/nozzle/1.png"));
        
        weaponTextures = new Texture[3];
        weaponTextures[0] = new Texture(Gdx.files.internal("weapons/guns/0.png"));
        weaponTextures[1] = new Texture(Gdx.files.internal("weapons/guns/1.png"));
        weaponTextures[2] = new Texture(Gdx.files.internal("weapons/guns/2.png"));
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
        this.flip = p .flip;

        // Starting positions
        this.x2 = this.x1 = 0;
        this.y2 = this.y1 = 0;
        r2 = r1 = 0;

    }

    // Spawn bullet(s)
    public void shoot(ArrayList<GunBullet> bullets, CustomEntity p) {
        if (MainGame.getTimeMs()-time_last_shot >= shootTime && ammo > 0 && !reloading)
            lightNozzle = true;
    }

    // Apply recoil after shot
    protected void applyRecoilForce(CustomEntity p) {
        p.pBody.applyForceX((p.flip?-1:1) * recoil/4);
    }

    // Get the remaining ammo
    public int getAmmoCount() {
        return ammo;
    }

    // Setter
    public void initHandle(int originX, int originY, float idleRot) {
        this.wpnImage.setOrigin(originX, originY);
        this.originX = originX;
        this.originY = originY;
        this.idleRot = idleRot;
    }
    public void initBackHandPos(int x, int y) {
        this.backHandPosX = x;
        this.backHandPosY = y;
    }
    public void setRecoilRot(float r) {
        this.recoilRot = r;
    }
    public void setHandsObject(Hands h) {
        this.hands = h;
    }
    public void setOffsets(int offsetStartX, int offsetEndX, int offsetStartY, int offsetEndY) {
        this.weaponStartX = offsetStartX;
        this.weaponEndX = offsetEndX;
        this.weaponStartY = offsetStartY;
        this.weaponEndY = offsetEndY;
    }

    // Getters
    public float getWeight() { return weight; }
    public float getRecoil() { return recoil; }
    public boolean isReloading() { return reloading; }

    // To get positions for drawing
    public void update(Feet f, CustomEntity p) {

        // If we are reloading then create a throwable
        if (reloading) {

            // Last 500ms of animation
            if (MainGame.getTimeMs()-reloadTimer > 270) {

                // Throw the weapon
                if (!madeThrowable) {
                    
                    madeThrowable = true;
                    Throwable t = new Throwable(p.getX()+CustomEntity.P_WIDTH/2 + (flip?1:-1)*(weaponEndX+60), p.getY() + weaponEndY + 50, weaponTextures[weaponNumber], flip);
                    gameWorld.addThrowable(t);
                    wpnImage.setVisible(false);
    
                // Spawn the new weapon back
                } else {

                    // Zoom it back into view
                    x2 = (p.flip ? 1 : -1) * (weaponStartX) + ( f.getBackX() - p.getX() - CustomEntity.P_WIDTH/2) * 0.7f;
                    y2 = weaponStartY;
                    r2 = idleRot;

                    
                }
                
            } else {

                x2 = (flip?1:-1) * (weaponEndX + 150);
                y2 = (weaponEndY + 100);
                r2 = 0;

            }


            // Back hand should still follow the player
            hands.setLerpPosBack(f.getFrontX()+(p.flip?1:-1) * 59, p.getY() + 45);

            // Lerp
            x1 += (x1-x2) * -0.045;
            y1 += (y1-y2) * -0.045;

        } else {

            // From reload
            madeThrowable = false;
            wpnImage.setVisible(true);

            // Set positions
            if (MainGame.getTimeMs()-time_last_shot > IDLE_TIME) {
    
                // In Animation
                x2 = (p.flip ? 1 : -1) * (weaponStartX) + ( f.getBackX() - p.getX() - CustomEntity.P_WIDTH/2) * 0.7f;
                y2 = weaponStartY;
                r2 = idleRot;
    
                // When walking the back hand is with front foot
                hands.setLerpPosBack(f.getFrontX()+(p.flip?1:-1) * 59, p.getY() + 45);
                
            }
            
            // Shooting position
            else {
    
                // Shooting positions
                x2 = (p.flip ? 1 : -1) * (weaponEndX);
                y2 = weaponEndY;
                r2 = 0;
                
                // Include recoil
                float timeDif = MainGame.getTimeMs()-time_last_shot;
                if (timeDif < shootTime) {
                    
                    x2 += spikeLerp(timeDif/(shootTime)) * -20 * (flip ? 1 : -1);
                    r2 += spikeLerp(timeDif/(shootTime)) * recoilRot;
                    
                }
    
                // Back hand position
                hands.setLerpPosBack(p.getX() + x2 + CustomEntity.P_WIDTH/2 + (p.flip?1:-1) * backHandPosX, p.getY() + weaponEndY + backHandPosY);
    
                x1 = x2;
                y1 = y2;
                r1 = r2;
    
            }
            
            // Lerp
            x1 += (x2-x1) * 0.2;
            y1 += (y2-y1) * 0.2;
            r1 += (r2-r1) * 0.2;

        }

        // Reloading / throwing weapon
        if (ammo <= 0) {

            if (!reloading)
                reloadTimer = (int) MainGame.getTimeMs();
            reloading = true;
            

            if (reloading && MainGame.getTimeMs()-reloadTimer >= reloadTime) {
                ammo = max_ammo;
                reloading = false;

                if (disposable) {
                    p.giveWeapon(
                        GunLibrary.pistol(p)
                    );
                }

            }

        }


        // Front hand is stuck to gun normally
        wpnImage.setPosition(p.getX()+CustomEntity.P_WIDTH/2 + (p.flip ? x1 : x1-originX*2), p.getY() + y1);
        hands.setFrontHandPos(wpnImage.getX() + wpnImage.getOriginX(), wpnImage.getY()+originY/2);

    }


    // Draw images at calculated positions
    public void draw(Batch sb, CustomEntity p) {
                
        // Draw at the positions
        wpnImage.setPosition(p.getX()+CustomEntity.P_WIDTH/2 + (p.flip ? x1 : x1-originX*2), p.getY() + y1);
        wpnImage.setRotation(p.flip ? r1 : 360-r1);
        wpnImage.setScaleX(p.flip ? 1 : -1);
        
        if (wpnImage.isVisible()) {
            wpnImage.draw(sb, 1);

        }

        // Draw nozzle
        if (lightNozzle) {
            
            int i = (int) Math.round(Math.random());
            sb.draw(nozzleFlash[i], p.getX() + CustomEntity.P_WIDTH/2 + (flip ? gun_length : -gun_length), wpnImage.getY()+35-nozzleFlash[i].getHeight()/2, (flip?1:-1) * nozzleFlash[i].getWidth(), nozzleFlash[i].getHeight());

            lightNozzle = false;
        }



    }


    // For creating throwables
    public static void setWorld(GameWorld w) {
        gameWorld = w;
    }

    // Lerp with spike for recoil
    public static float spikeLerp(float t) {

        if (t <= 0.5)
            return 1f - (float) Math.pow(t/0.5, 3);
        return 0f;

    }

}
