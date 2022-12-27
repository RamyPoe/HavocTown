package com.project.game.Weapons;


public class GunLibrary {

    public static Weapon rifle1() {
        return new Rifle(0, 1000, 1, 0, 100, 10, false, 99, 3000, 3);
    }

    public static Weapon rifle2() {
        return new Rifle(0, 100, 5, 0, 50, 20, false, 99, 1000, 10);
    }

}
