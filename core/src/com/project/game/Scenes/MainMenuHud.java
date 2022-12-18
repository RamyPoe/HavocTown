package com.project.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;

public class MainMenuHud implements Disposable {
    
    public Stage stage;
    public Viewport viewport;
    private OrthographicCamera cam;

    public MainMenuHud(SpriteBatch sb) {

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Stage
        stage = new Stage(viewport, sb);

        // Font and Label
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/menu-font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 96;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2f;
        parameter.borderColor = Color.BLACK;
        
        BitmapFont bFont = generator.generateFont(parameter); 
        generator.dispose();

        Label.LabelStyle style = new Label.LabelStyle(bFont, Color.WHITE);
        Label lbl = new Label("HELLO WORLD!", style);

        // Table
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(lbl).expandX();

        // Add table to stage
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
