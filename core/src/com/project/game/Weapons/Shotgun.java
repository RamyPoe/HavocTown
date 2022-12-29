package com.project.game.Weapons;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.project.game.Player.CustomEntity;

public class Shotgun extends Weapon {

    // Shotgun specific fields
    private int pellets;
    private float spreadAngle;
    private int reach;

    public Shotgun(int weaponNumber, int shootTime, int weight, int recoil, int gun_length, int bulletSpeed,
            boolean disposable, int max_ammo, int reloadTime, float strength, int pellets, float spreadAngle,
            int reach, CustomEntity p) {

        // Create the weapon with image
        super(
            weaponNumber, shootTime, weight, recoil, gun_length, bulletSpeed,
            disposable, max_ammo, reloadTime, strength, p
        );
        
        // Save weapon number for offsets
        this.weaponNumber = weaponNumber;

        // Save shotgun fields
        this.pellets = pellets;
        this.spreadAngle = spreadAngle;
        this.reach = reach;


    }

    @Override
    public void shoot(ArrayList<GunBullet> bullets, CustomEntity p) {

        // Get current time
        long millis = System.nanoTime() / 1_000_000;

        if (millis-time_last_shot >= shootTime && ammo > 0) {

            time_last_shot = millis;
            ammo--;


            for (int i = 0; i < pellets; i++) {

                bullets.add(new GunBullet(
                    p.getX() + CustomEntity.P_WIDTH/2 + (flip ? 1 : -1) * gun_length,
                    p.getY() + 30,
                    bulletSpeed,
                    reach,
                    (spreadAngle/(float)pellets)*i - spreadAngle/2,
                    1,
                    strength,
                    flip
                ));

            }

        }

        // Reload
        if (!disposable && ammo <= 0) {
            time_last_shot = millis + reloadTime;
            ammo = max_ammo;
        }

        // Lose the weapon and go back to default
        else if (disposable) {
            // TODO: LOSE DISPOSABLE GUNS
        }

        // To be drawn
        lightNozzle = true;
        
    }
    
}