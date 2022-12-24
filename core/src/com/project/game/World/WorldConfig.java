package com.project.game.World;

import java.io.FileWriter;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class WorldConfig {
    
    // Where to load map files from
    private String texturePrefix;
    private float yOff1, yOff2, yOff3;

    // Where to place the platforms
    private ArrayList<Box> platforms;

    // Constructor
    public WorldConfig() {
        platforms = new ArrayList<>();
    }

    // Adding a platform
    public void addPlatform(float x, float y, float w, float h, boolean passable) {
        Box b = new Box(x, y, w, h, passable);
        platforms.add(b);
    }


    // Setters
    public void setTexturePrefix(String s) {
        this.texturePrefix = s;
    }
    public void setOffY(float y1, float y2, float y3) {
        yOff1 = y1; yOff2 = y2; yOff3 = y3;
    }

    // Getters
    public String getTexturePrefix() {
        return texturePrefix;
    }
    public ArrayList<Box> getPlatformArray() {
        return platforms;
    }
    public float getOffY1() { return yOff1; }
    public float getOffY2() { return yOff2; }
    public float getOffY3() { return yOff3; }


    // Creating Config files
    public static void writeConfigFile(WorldConfig c) {

        // Get Json data
        Json json = new Json();
        json.setOutputType(OutputType.json);
        String data = json.prettyPrint(c);

        // Write to file
        try {

            String filename = "assets/maps/" + c.getTexturePrefix() + ".json";
            FileHandle file = Gdx.files.local(filename);
            file.writeString(data, false);

        } catch (Exception e) {
            // Log and stop if we have error
            e.printStackTrace();
            System.exit(-1);
        }

    }

    // Reading from config file
    public static WorldConfig readConfigFile(String tPrefix) {

        // Read the file
        try {

            String filename = "assets/maps/" + tPrefix + ".json";
            FileHandle file = Gdx.files.local(filename);
            String data = file.readString();

            Json json = new Json();
            json.setOutputType(OutputType.json);
            WorldConfig c = json.fromJson(WorldConfig.class, data);
            return c;

        } catch (Exception e) {
            // Log and stop if we have error
            e.printStackTrace();
            System.exit(-1);
            return null;
        }


    }

}

// A way of storing the platform coordinates and size
class Box {
    public float x, y, w, h;
    public boolean passable;
    public Box() {}
    public Box(float x, float y, float w, float h, boolean passable) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.passable = passable;
    }
}