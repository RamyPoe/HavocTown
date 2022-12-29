package com.project.game.Weapons;

import com.project.game.Player.CustomEntity;

public class GunLibrary {

    public static Weapon rifle1(CustomEntity p) {
        Weapon w = new Rifle(0, 240, 1, 0, 100, 15, false, 99, 3000, 5, p);
        w.initHandle(15, 29, -30);
        return w;
    }
    
    public static Weapon rifle2(CustomEntity p) {
        Weapon w = new Rifle(0, 100, 5, 0, 75, 20, false, 99, 1000, 10, p);
        w.initHandle(15, 29, -30);
        return w;
    }

    public static Weapon shotgun1(CustomEntity p) {
        return new Shotgun(0, 100, 5, 0, 50, 20, false, 99, 1000, 10, 5, 45, 100, p);
    }

}
