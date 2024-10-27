package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static game.com.Main.screens;

public class Home_screen implements Screen {
    private Stage stage;
    private Texture image;
    private SpriteBatch batch;
    private Image imgActor;
    private Viewport viewport;
    private Skin skin;
    private TextButton playButton, loadButton, quitButton;
    private Table table;
    private Texture buttonUpTexture, buttonDownTexture;
    private BitmapFont font;

    private Main main;
   private Main reference;
    public Home_screen(Main main) {
        viewport = new FitViewport(1600, 900);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        this.main = main;


        buttonUpTexture = new Texture(Gdx.files.internal("images/level_screen.png"));
        buttonDownTexture = new Texture(Gdx.files.internal("images/level_screen.png"));

        font = new BitmapFont();
        font.getData().setScale(2f);


        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = new TextureRegionDrawable(buttonUpTexture);
        buttonStyle.down = new TextureRegionDrawable(buttonDownTexture);
        buttonStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;

        playButton = new TextButton("Play", buttonStyle);
        loadButton = new TextButton("Load Game", buttonStyle);
        quitButton = new TextButton("Quit", buttonStyle);

        playButton.setSize(250, 100);
        loadButton.setSize(250, 100);
        quitButton.setSize(250, 100);

        table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(playButton).size(playButton.getWidth(), playButton.getHeight()).padBottom(30);
        table.row();
        table.add(loadButton).size(loadButton.getWidth(), loadButton.getHeight()).padBottom(30);
        table.row();
        table.add(quitButton).size(quitButton.getWidth(), quitButton.getHeight());

        stage.addActor(table);

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Play");
                screens.put("Level_screen", new Level_screen(main));
                main.setScreen(screens.get("Level_screen"));
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Load Game clicked");
                screens.put("Load_screen", new load_screen(main));
                main.setScreen(screens.get("Load_screen"));
            }
        });


        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
        image = new Texture(Gdx.files.internal("images/angry_bird.png"));
        imgActor = new Image(image);

        imgActor.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        imgActor.setPosition(0, 0);

        stage.addActor(imgActor);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        imgActor.setZIndex(0);
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
        image.dispose();
        stage.dispose();
        buttonUpTexture.dispose();
        buttonDownTexture.dispose();
        font.dispose();
    }

    public TextButton getPlayButton() {
        return playButton;
    }

    public TextButton getLoadButton() {
        return loadButton;
    }

    public TextButton getQuitButton() {
        return quitButton;
    }
}
