package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static game.com.Main.saved_game;

public class load_screen implements Screen {
    private Main main;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture selectLevelTexture;
    private ImageButton level1Button, level2Button, level3Button;

    public load_screen(Main main) {
        this.main = main;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("images/level1.png"));

        selectLevelTexture = new Texture(Gdx.files.internal("images/select_level.png"));
        Image selectLevelImage = new Image(new TextureRegionDrawable(selectLevelTexture));
        selectLevelImage.setPosition(Gdx.graphics.getWidth() / 2f - selectLevelTexture.getWidth() / 2f, Gdx.graphics.getHeight() - 150);
        stage.addActor(selectLevelImage);

        level1Button = createImageButton("images/1.png");
        level2Button = createImageButton("images/2.png");
        level3Button = createImageButton("images/3.png");

        float buttonWidth = 300;
        float buttonHeight = 300;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        level1Button.setSize(buttonWidth, buttonHeight);
        level1Button.setPosition(screenWidth / 2 - buttonWidth / 2 - 350, screenHeight / 2 - buttonHeight / 2);

        level2Button.setSize(buttonWidth, buttonHeight);
        level2Button.setPosition(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 - buttonHeight / 2);

        level3Button.setSize(buttonWidth, buttonHeight);
        level3Button.setPosition(screenWidth / 2 - buttonWidth / 2 + 350, screenHeight / 2 - buttonHeight / 2);

        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);

        addButtonListeners();
    }

    private ImageButton createImageButton(String imagePath) {
        Texture buttonTexture = new Texture(Gdx.files.internal(imagePath));
        TextureRegionDrawable drawable = new TextureRegionDrawable(buttonTexture);
        return new ImageButton(drawable);
    }

    private void addButtonListeners() {
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (saved_game.containsKey("Level1Screen")) {
                    Level1Screen s= (Level1Screen) saved_game.get("Level1Screen");
                    s.togglePauseMenu(false);
                    main.setScreen(saved_game.get("Level1Screen"));
                } else {
                    showOopsScreen();
                }
            }
        });

        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (saved_game.containsKey("Level2Screen")) {
                    main.setScreen(saved_game.get("Level2Screen"));
                } else {
                    showOopsScreen();
                }
            }
        });

        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (saved_game.containsKey("Level3Screen")) {
                    main.setScreen(saved_game.get("Level3Screen"));
                } else {
                    showOopsScreen();
                }
            }
        });
    }
    private void showOopsScreen() {

        System.out.println("Oops! Sorry, this level is not available.");
        main.setScreen(new Ops_Screen(main)); // Return to home screen
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);  // Black background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Fullscreen background
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        selectLevelTexture.dispose();
        batch.dispose();
        stage.dispose();
    }
}
