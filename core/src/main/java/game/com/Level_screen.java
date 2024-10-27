package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static game.com.Main.screens;

public class Level_screen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Viewport viewport;
    private BitmapFont font;

    private Main main;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Image selectLevelsImage;

    private TextButton level1Button, level2Button, level3Button;
    private Table table;

    public Level_screen(Main main) {
        this.main = main;

        viewport = new FitViewport(1600, 900);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        backgroundTexture = new Texture(Gdx.files.internal("images/level_screen.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());

        Texture selectLevelsTexture = new Texture(Gdx.files.internal("images/select_level.png"));
        selectLevelsImage = new Image(selectLevelsTexture);

        font = new BitmapFont();
        font.getData().setScale(2f);

        TextButtonStyle level1ButtonStyle = new TextButtonStyle();
        level1ButtonStyle.font = font;
        level1ButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("images/1.png")));
        level1ButtonStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;

        TextButtonStyle level2ButtonStyle = new TextButtonStyle();
        level2ButtonStyle.font = font;
        level2ButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("images/2.png")));
        level2ButtonStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;

        TextButtonStyle level3ButtonStyle = new TextButtonStyle();
        level3ButtonStyle.font = font;
        level3ButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("images/3.png")));
        level3ButtonStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;


        level1Button = new TextButton("", level1ButtonStyle);
        level2Button = new TextButton("", level2ButtonStyle);
        level3Button = new TextButton("", level3ButtonStyle);

        table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(selectLevelsImage).padBottom(30).colspan(3).center();
        table.row();


        table.add(level1Button).size(100, 100).padRight(200);
        table.add(level2Button).size(100, 100).padRight(200);
        table.add(level3Button).size(100, 100);


        stage.addActor(backgroundImage);
        stage.addActor(table);


        setupButtonListeners();
    }

    private void setupButtonListeners() {

        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("You have chosen Level 1");
                screens.put("Level1Screen", new Level1Screen(main)); // Switch to Level1Screen
                main.setScreen(screens.get("Level1Screen")); // Set the new screen
            }
        });


        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("You have chosen Level 2");
                screens.put("Level2Screen", new Level2Screen(main)); // Switch to Level2Screen
                main.setScreen(screens.get("Level2Screen")); // Set the new screen
            }
        });

        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("You have chosen Level 3");
                screens.put("Level3Screen", new Level3Screen(main)); // Switch to Level3Screen
                main.setScreen(screens.get("Level3Screen")); // Set the new screen
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1f);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}
