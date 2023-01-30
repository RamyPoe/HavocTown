package com.project.game.Scenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.game.MainGame;
import com.project.game.Player.CustomEntity;
import com.project.game.Player.PlayerConfig;
import com.project.game.Scenes.CosmeticStruct.CosmType;

public class SkinSelectHud implements Disposable {
 
    // If we have popup
    private boolean isPopup;

    // Stage setup
    public Stage stage;
    public Viewport viewport;
    private OrthographicCamera cam;

    // Mini HUD
    SkinSelector SSp1, SSp2;

    // Search bar
    private SearchPopup searchPopup;

    public SkinSelectHud(SpriteBatch sb) {
        
        // For prespective
        cam = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, cam);

        // Stage
        stage = new Stage(viewport, sb);

        // Table
        Table table = new Table();
        table.setFillParent(true);
        table.center().left();

        // Start without popup
        isPopup = false;

        // Each have their own hud
        SSp1 = new SkinSelector(MainGame.playerConfig1);
        SSp2 = new SkinSelector(MainGame.playerConfig2);

        // Shared search bar
        searchPopup = new SearchPopup();
        searchPopup.setPosition(MainGame.V_WIDTH/2-SearchPopup.WIDTH/2, MainGame.V_HEIGHT/2-SearchPopup.HEIGHT/2);
        searchPopup.setVisible(false);

        // Add to Table
        table.add(SSp1).padLeft(50);
        table.add(SSp2).padLeft(70);

        // Add table to stage
        stage.addActor(table);
        stage.addActor(searchPopup);

        // Debug
        table.setDebug(false);

    }

    // Sperate logic from drawing
    public void update() {

        System.out.println(MainGame.playerConfig1.playerColorNumber);

        // Set popup based on boolean
        searchPopup.setVisible(isPopup);

        // See if close has been pressed
        if (searchPopup.checkClicked()) {
            isPopup = false;
        }

        // Don't check clicks when we have popup
        if (isPopup) { 
            SSp1.checkClicked();
            SSp2.checkClicked();
            return;
        }

        // Check button clicks
        if (SSp1.checkClicked()) {
            searchPopup.setConfig(SSp1.getConfig());
            isPopup = true;
        }
        if (SSp2.checkClicked()) {
            searchPopup.setConfig(SSp2.getConfig());
            isPopup = true;
        }


    }

    // Draw to screen
    public void draw(Batch b) {

        // Seperate logic from drawing
        update();

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
    }

}

@SuppressWarnings("unchecked")
class SearchPopup extends Group implements Disposable {

    // Window constants
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;

    // Config that we're changing
    PlayerConfig pConfig;

    // Styles for buttons and fields
    private static TextButtonStyle btnStyle;
    private static TextFieldStyle fieldStyle;
    static {
        // Create instances
        btnStyle = new TextButtonStyle();
        fieldStyle = new TextFieldStyle();
        
        // File handle
        FileHandle fontFileHandle = Gdx.files.internal("fonts/skinselect-font.ttf");

        // Load Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFileHandle);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        // Set parameters
        parameter.size = 48;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 2f;
        parameter.borderColor = Color.GRAY;
        
        // Save font
        btnStyle.font = generator.generateFont(parameter);

        // Set parameter for TextField
        parameter.color = Color.WHITE;
        parameter.borderWidth = 0f;
        parameter.borderColor = Color.WHITE;

        // Save font
        fieldStyle.font = generator.generateFont(parameter);
        fieldStyle.fontColor = Color.WHITE;
        fieldStyle.focusedFontColor = Color.WHITE;
        fieldStyle.disabledFontColor = Color.GRAY;

        // Textures
        fieldStyle.cursor = new TextureRegionDrawable(MainGame.createTexture(3, 30, Color.BLACK));
        fieldStyle.background = new TextureRegionDrawable(MainGame.createTexture(800, 100, Color.valueOf("aaaaaa")));
        
        // Clear memory
        generator.dispose();
        generator = null;
        parameter = null;
    }

    // Input
    TextField searchField;
    private TextButton closeBtn;

    // If close button pressed
    private boolean clicked = false;

    // Background
    private static Texture backTexture;
    static {
        backTexture = MainGame.createTexture(WIDTH, HEIGHT, Color.valueOf("666666"));
    }
    private Image backImage;

    // Player textures
    private static ArrayList<CosmeticStruct> allTextures;
    private ArrayList<CosmeticStruct> filteredTextures;

    // Constructor
    public SearchPopup() {

        // Super
        super();

        // Load all textures for searching
        loadAllTextures();
        filteredTextures = (ArrayList<CosmeticStruct>) allTextures.clone();

        // Create image
        backImage = new Image(backTexture);

        // Create close button
        closeBtn = new TextButton("X", btnStyle);
        closeBtn.setPosition(WIDTH-40, HEIGHT-60);

        // Button listener
        closeBtn.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicked = true;
            };
        });

        // Create the text field
        searchField = new TextField("", fieldStyle);
        searchField.setSize(800, 100);
        searchField.setPosition(WIDTH/2-800/2, HEIGHT-150);
        searchField.setBlinkTime(0.5f);
        searchField.setAlignment(Align.center);

        // Add to group
        this.addActor(backImage);
        this.addActor(closeBtn);
        this.addActor(searchField);

    }

    // Check if close button has been pressed
    public boolean checkClicked() {
        boolean temp = clicked;
        clicked = false;
        return temp;
    }

    // Based on what we're changing
    public void setConfig(PlayerConfig pConfig) {
        this.pConfig = pConfig;
    }

    // So we can draw the texture array
    @Override
    public void draw(Batch batch, float parentAlpha) {
    
        // Draw stuff from before too
        super.draw(batch, parentAlpha);
        

        // Draw constants
        float scale = 1.6f;

        int sx = MainGame.V_WIDTH/2 - WIDTH/2 + 100;
        int sxM = MainGame.V_WIDTH/2 + WIDTH/2 - 100;
        int sy = MainGame.V_HEIGHT/2 + HEIGHT/2 - 300;
        int padx = 30; int pady = 160;

        int cx = sx;
        int cy = sy;

        int i = 0;
        while (i < filteredTextures.size()) {

            // Add padding
            cx += (cx == sx ? 0 : padx);

            // New row
            if (filteredTextures.get(i).texture.getWidth()*scale + cx > sxM) {
                cx = sx;
                cy -= pady;
                continue;
            }

            // Check for click
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && mouseInRect(cx, cy, (int) (filteredTextures.get(i).texture.getWidth()*scale), (int) (filteredTextures.get(i).texture.getHeight()*scale))) {

                // Change the config
                switch(filteredTextures.get(i).type) {
                    case HAT:
                        pConfig.hatSkin = (short) filteredTextures.get(i).index;
                        break;
                    case SHIRT:
                        pConfig.shirtSkin = (short) filteredTextures.get(i).index;
                        break;
                    case COLOR:
                        pConfig.playerColorNumber = (short) filteredTextures.get(i).index;
                        break;
                    case FACE:
                        pConfig.faceSkin = (short) filteredTextures.get(i).index;
                        break;
                }

                // Simulate close button press
                clicked = true;


            }

            // Continue on this row
            batch.draw(filteredTextures.get(i).texture, cx, cy, filteredTextures.get(i).texture.getWidth()*scale, filteredTextures.get(i).texture.getHeight()*scale);
            cx += filteredTextures.get(i).texture.getWidth()*scale;

            

            // Increment for next iteration
            i++;

        }

        // Sort based on the text
        filteredTextures = searchMatch (
            sortAlphabetical(allTextures),
            searchField.getText().strip().replace(" ", "")
        );

    }

    // Check if mouse is in position
    public boolean mouseInRect(int x, int y, int w, int h) {

        int mX = Gdx.input.getX();
        int mY = MainGame.V_HEIGHT - Gdx.input.getY();

        if (mX > x && mX < x+w && mY > y && mY < y+h) {
            return true;
        }
        return false;

    }

    // Searching for match
    public ArrayList<CosmeticStruct> searchMatch(ArrayList<CosmeticStruct> aList, String match) {

        // Case sensitivity
        match = match.toLowerCase();

        // Pointer to swap with
        int j = 0;

        for (int i = 0; i < aList.size(); i++) {

            if (aList.get(i).containsKeyword(match)) {
                Collections.swap(aList, i, j);
                j++;
            }

        }

        return aList;

    }

    // Sorting alphabetically
    public ArrayList<CosmeticStruct> sortAlphabetical(ArrayList<CosmeticStruct> aList) {

        // Clone
        ArrayList<CosmeticStruct> fList = (ArrayList<CosmeticStruct>) aList.clone();


        // Bubble sort
        boolean sorted = false;
        while (!sorted) {
            sorted = true;

            for (int i = 1; i < fList.size(); i++) {

                if (fList.get(i).toString().compareTo(fList.get(i-1).toString()) <= -1) {
                    sorted = false;
                    Collections.swap(fList, i, i-1);
                }

            }

        }

        // Return sorted
        return fList;

    }

    // Load them when we make class
    private static void loadAllTextures() {
        if (allTextures != null) { return; }

        // Create array instance
        allTextures = new ArrayList<>();

        // Colors
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/0.png")), CosmType.COLOR, 0).addWord("pink") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/1.png")), CosmType.COLOR, 1).addWord("purple") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/2.png")), CosmType.COLOR, 2).addWord("red") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/3.png")), CosmType.COLOR, 3).addWord("brown") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/4.png")), CosmType.COLOR, 4).addWord("yellow") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/5.png")), CosmType.COLOR, 5).addWord("aqua") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/6.png")), CosmType.COLOR, 6).addWord("blue") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("hud/palette/7.png")), CosmType.COLOR, 7).addWord("green") );
        
        // Faces
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/face/0.png")), CosmType.FACE, 0).addWord("serious") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/face/1.png")), CosmType.FACE, 1).addWord("noodle") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/face/2.png")), CosmType.FACE, 2).addWord("blindfold") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/face/3.png")), CosmType.FACE, 3).addWord("nice") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/face/4.png")), CosmType.FACE, 4).addWord("santa") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/face/5.png")), CosmType.FACE, 5).addWord("moustache") );
        
        // Hat
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/hat/0.png")), CosmType.HAT, 0).addWord("santa") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/hat/1.png")), CosmType.HAT, 1).addWord("double") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/hat/2.png")), CosmType.HAT, 2).addWord("clown") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/hat/3.png")), CosmType.HAT, 3).addWord("pan") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/hat/4.png")), CosmType.HAT, 4).addWord("sombrero") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/hat/5.png")), CosmType.HAT, 5).addWord("judge") );
        
        // Shirt
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/shirt/0.png")), CosmType.SHIRT, 0).addWord("suit") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/shirt/1.png")), CosmType.SHIRT, 1).addWord("gi") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/shirt/2.png")), CosmType.SHIRT, 2).addWord("caveman") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/shirt/3.png")), CosmType.SHIRT, 3).addWord("santa") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/shirt/4.png")), CosmType.SHIRT, 4).addWord("summer") );
        allTextures.add( new CosmeticStruct( new Texture (Gdx.files.internal("skins/shirt/5.png")), CosmType.SHIRT, 5).addWord("office") );
        

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

}

class CosmeticStruct implements Disposable {

    // Each will be one of these
    public static enum CosmType {HAT, SHIRT, COLOR, FACE};

    public Texture texture;
    public CosmType type;
    public int index;
    public ArrayList<String> keywords;

    public CosmeticStruct(Texture texture, CosmType type, int index) {
        this.texture = texture;
        this.type = type;
        this.index = index;

        keywords = new ArrayList<>();

        String defaultWord = "";
        switch(type) {
            case HAT:
                defaultWord = "hat";
                break;
            case SHIRT:
                defaultWord = "shirt";
                break;
            case COLOR:
                defaultWord = "color";
                break;
            case FACE:
                defaultWord = "face";
                break;
            default:
                defaultWord = "";
        }

        keywords.add(defaultWord);
    }

    public CosmeticStruct addWord(String w) {
        keywords.add(w);
        return this;
    }

    // Contains within any keyword
    public boolean containsKeyword(String m) {
        for (String s : keywords) {
            if (s.contains(m)) { return true; }
        }
        return false;
    }

    @Override
    public String toString() {
        return keywords.get(1);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}

class SkinSelector extends Group {

    // Static textures
    private static Texture backTexture;
    static {
        backTexture = MainGame.createTexture(300, 600, Color.WHITE);
    }

    // Images
    private Image backImg;

    // Which character we are changing
    private PlayerConfig pConfig;
    private CustomEntity pModel;

    // Edit button to pullup search bar
    private static TextButtonStyle btnStyle;
    static {
        btnStyle = new TextButtonStyle();
        
        // File handle
        FileHandle fontFileHandle = Gdx.files.internal("fonts/skinselect-font.ttf");

        // Load Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFileHandle);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        // Set parameters
        parameter.size = 96;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 2f;
        parameter.borderColor = Color.GRAY;
        
        // Save font
        btnStyle.font = generator.generateFont(parameter);
        
        // Clear memory
        generator.dispose();
        generator = null;
        parameter = null;
    }
    private TextButton editBtn;

    // Click state to be read
    private boolean clicked = false;

    // Constructor
    public SkinSelector(PlayerConfig pConfig) {

        // Super
        super();

        // Save field
        this.pConfig = pConfig;


        // Create image
        backImg = new Image(backTexture);

        // Create button
        editBtn = new TextButton("EDIT", btnStyle);
        editBtn.setSize(200, 100);
        editBtn.setPosition(50, 100);

        // Button listener
        editBtn.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicked = true;
            };
        });

        // Add to group
        this.addActor(backImg);
        this.addActor(editBtn);


        // Set size
        this.setSize(backImg.getWidth(), backImg.getHeight());

    }

    // Check if our button has been pressed
    public boolean checkClicked() {
        boolean temp = clicked;
        clicked = false;
        return temp;
    }

    // Getter to give to search
    public PlayerConfig getConfig() {
        return this.pConfig;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // Draw what we were before
        super.draw(batch, parentAlpha);

        // Create model to show
        if (pModel != null) {pModel.dispose();}
        pModel = new CustomEntity(
            this.getX() - CustomEntity.P_WIDTH/2 + this.getWidth()/2,
            this.getY() + this.getHeight()/2,
            pConfig
        );
        pModel.flip = true;

        // Draw the player model
        for (int i = 0; i < 10; i++) { pModel.updatePosModel(); }
        pModel.draw(batch);

    }


}
