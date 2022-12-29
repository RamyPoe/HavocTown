package com.project.game.Weapons;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;

public class Rifle extends Weapon {    

    public Rifle(int weaponNumber, int shootTime, int weight, int recoil, int gun_length, int bulletSpeed,
            boolean disposable, int max_ammo, int reloadTime, float strength, CustomEntity p) {

        // Create the weapon given info
        super(
            weaponNumber, shootTime, weight, recoil, gun_length,bulletSpeed,
            disposable, max_ammo, reloadTime, strength, p
        );

        // Save weapon number for offsets
        this.weaponNumber = weaponNumber;

    }

    @Override
    public void shoot(ArrayList<GunBullet> bullets, CustomEntity p) {

        // Super
        super.shoot(bullets, p);

        // Get current time
        long millis = MainGame.getTimeMs();

        if (millis-time_last_shot >= shootTime && ammo > 0) {

            time_last_shot = millis;
            ammo--;

            bullets.add(new GunBullet(
                p.getX() + CustomEntity.P_WIDTH/2 + (flip ? 1 : -1) * gun_length,
                p.getY() + 68,
                bulletSpeed,
                4000,
                0,
                1,
                strength,
                flip
            ));
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

    }


    // Draw the gun
    @Override
    public void draw(Batch sb, CustomEntity p) {

        // Draw gun
        super.draw(sb, p);


    }

}
