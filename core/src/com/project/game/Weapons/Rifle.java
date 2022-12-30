package com.project.game.Weapons;

import java.util.ArrayList;

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

        if (millis-time_last_shot >= shootTime && ammo > 0 && !reloading) {

            // Apply recoil to player
            applyRecoilForce(p);

            // So we don't shoot again
            time_last_shot = millis;
            ammo--;

            // Spawn bullets
            bullets.add(new GunBullet(
                p.getX() + CustomEntity.P_WIDTH/2 + (flip ? 1 : -1) * (gun_length - 25),
                p.getY() + weaponEndY + 40,
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

            if (!reloading)
                reloadTimer = (int) MainGame.getTimeMs();
            reloading = true;
            

            if (reloading && MainGame.getTimeMs()-reloadTimer >= reloadTime) {
                ammo = max_ammo;
                reloading = false;
            }

        }

        // Lose the weapon and go back to default
        else if (disposable) {
            // TODO: LOSE DISPOSABLE GUNS
        }

    }


}
