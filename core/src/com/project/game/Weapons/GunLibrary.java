package com.project.game.Weapons;

import com.project.game.Player.CustomEntity;

public class GunLibrary {

    public static Weapon pistol(CustomEntity p) {
        Weapon w = new Rifle(0, 240, 1, 3, 65, 17, false, 12, 3000, 5, p);
        w.initHandle(15, 29, -20);
        w.initBackHandPos(30, 10);
        w.setRecoilRot(20);
        w.setOffsets(-23, 20, 20, 40);
        return w;
    }
    
    public static Weapon ak47(CustomEntity p) {
        Weapon w = new Rifle(1, 100, 3, 5, 105, 20, false, 20, 1000, 5, p);
        w.initHandle(49, 27, -30);
        w.initBackHandPos(110, 30);
        w.setRecoilRot(5);
        w.setOffsets(-58, -30, 30, 30);
        return w;
    }
    
    public static Weapon ks23(CustomEntity p) {
        Weapon w = new Shotgun(2, 600, 5, 10, 25, 20, false, 99, 1000, 10, 5, 45, 100, p);
        w.initHandle(29, 18, -25);
        w.initBackHandPos(60, 16);
        w.setRecoilRot(25);
        w.setOffsets(-23, 0, 26, 40);
        return w;
    }

}
