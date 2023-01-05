package com.project.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButton extends TextButton {

    private String text, description;
    private static BitmapFont buttonFont, descriptionFont;
    private static TextButtonStyle txtBtnStyle; 

    // Load assets
    static {
        // File handle
        FileHandle fontFileHandle = Gdx.files.internal("fonts/menu-font.ttf");

        // Load Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFileHandle);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        // Set parameters
        parameter.size = 96;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2f;
        parameter.borderColor = Color.BLACK;
        
        // Save font
        buttonFont = generator.generateFont(parameter); 
        
        // Set parameters
        parameter.size = 32;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 0;
        parameter.borderColor = Color.WHITE;
        
        // Save font
        descriptionFont = generator.generateFont(parameter); 

        // De-allocate
        parameter = null;
        generator.dispose();
        generator = null;

        // Button style
        txtBtnStyle = new TextButtonStyle();
        txtBtnStyle.font = buttonFont;
    }


    public MenuButton(String text, String description) {

        // Super
        super(text, txtBtnStyle);

        // Text specific to this button
        this.text = text;
        this.description = description;

        // For mouse cursor changing
        addEventListener();

    }

    public void addEventListener() {

        addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Hand);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
            }

        });

    }


}
