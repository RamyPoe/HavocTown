package com.project.game.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;

public class PlayerHud implements Disposable {

    // Stage setup
    public Stage stage;
    public Viewport viewport;
    private OrthographicCamera cam;

    // Array of huds for disposing
    private Array<SingleHud> hudArray;

    // Constructor
    public PlayerHud(SpriteBatch sb, Array<CustomEntity> players) {

        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Stage
        stage = new Stage(viewport, sb);

        // Table
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.padBottom(20);

        
        // Add huds to table
        hudArray = new Array<>(false, 4, SingleHud.class);
        for (int i = 0; i < players.size; i++) {
            SingleHud sh = new SingleHud(players.get(i));
            hudArray.add(sh);
            table.add(sh).padLeft(i == 0 ? 0 : 45);
        }


        // Debug option
        table.setDebug(false);

        // Add table to stage
        stage.addActor(table);

    }

    public void draw() {

        // For animations
        stage.act();

        // Get batch ready
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        
        // Draw GUI
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        for (SingleHud sh : hudArray) {
            sh.dispose();
        }
        hudArray.clear();
    }

}

class SingleHud extends Group implements Disposable {

    // Textures
    private static Texture backTexture, forgTexture;
    private Texture palTexture;

    // Load static textures
    static {
        backTexture = new Texture(Gdx.files.internal("hud/0.png"));
        forgTexture = new Texture(Gdx.files.internal("hud/1.png"));
    }


    // Images
    private Image backImg, forgImg;
    private Image paletteImg;

    // Player config
    private CustomEntity player;

    // Label styles
    private static LabelStyle nameStyle, livesStyle, ammoStyle;
    static {

        // File handle
        FileHandle fontFileHandle = Gdx.files.internal("fonts/hud-font.ttf");
        
        // Load Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFileHandle);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        // Name Style
        //========================================
        parameter.size = 18;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 0f;
        
        nameStyle = new Label.LabelStyle();
        nameStyle.fontColor = parameter.color;
        nameStyle.font = generator.generateFont(parameter); 

        // Lives Style
        //========================================
        parameter.size = 40;
        parameter.color = Color.RED;
        parameter.borderWidth = 1.2f;
        parameter.borderColor = parameter.color;
        
        livesStyle = new Label.LabelStyle();
        livesStyle.fontColor = parameter.color;
        livesStyle.font = generator.generateFont(parameter); 

        // Ammo Style
        //========================================
        parameter.size = 30;
        parameter.color = Color.ORANGE;
        parameter.borderWidth = 0f;
        
        ammoStyle = new Label.LabelStyle();
        ammoStyle.fontColor = parameter.color;
        ammoStyle.font = generator.generateFont(parameter); 

        // Disposal
        generator.dispose();
        generator = null;
        parameter = null;

    }

    // Text labels
    private Label nameLbl, livesLbl, ammoLbl;

    // Constructor
    public SingleHud(CustomEntity player) {

        // Super
        super();

        // Save fields
        this.player = player;

        // Create images from textures
        backImg = new Image(backTexture);
        forgImg = new Image(forgTexture);

        palTexture =  new Texture( Gdx.files.internal("hud/palette/" + player.getPlayerConfig().playerColorNumber + ".png") );
        paletteImg = new Image(palTexture);
        
        // Create Labels
        nameLbl = new Label("Player 1", nameStyle);
        livesLbl = new Label("99", livesStyle);
        ammoLbl = new Label("40", ammoStyle);
        
        
        // Position relatively
        forgImg.setPosition(85, 20);
        paletteImg.setPosition(20, 40);

        nameLbl.setPosition(90, 85);
        livesLbl.setPosition(90, 30);
        ammoLbl.setPosition(155, 45);

        // Add to this group
        this.addActor(backImg);
        this.addActor(forgImg);
        this.addActor(paletteImg);

        this.addActor(nameLbl);
        this.addActor(livesLbl);
        this.addActor(ammoLbl);

        // Set size
        this.setSize(backImg.getWidth(), backImg.getHeight());


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // Add logic to change labels
        ammoLbl.setText("" + player.getAmmoCount());

        // Maintain same drawing
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        palTexture.dispose(); 
    }

}