package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static game.com.Main.screens;

public class Lose_Screen implements Screen {

    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Main main;

    protected ImageButton retryButton, exitButton;

    private Texture ExitTexture;
    private Texture retryTexture;

    private Stage stage;

    public Lose_Screen(Main main) {
        this.main = main;
        backgroundTexture = new Texture(Gdx.files.internal("images/lose_screen.png")); // Load the background image
        batch = new SpriteBatch();

        ExitTexture = new Texture(Gdx.files.internal("images/exit.png"));
        retryTexture = new Texture(Gdx.files.internal("images/RETRY.png"));

        exitButton = new ImageButton(new TextureRegionDrawable(ExitTexture));
        retryButton = new ImageButton(new TextureRegionDrawable(retryTexture));

        exitButton.setSize(200, 100);
        retryButton.setSize(200, 100);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        exitButton.setPosition(100, stage.getViewport().getWorldHeight()/2);
        retryButton.setPosition(stage.getViewport().getWorldWidth() - 500, stage.getViewport().getWorldHeight()/2);

        stage.addActor(exitButton);
        stage.addActor(retryButton);

        exitButton.addListener(event -> {
            if (exitButton.isPressed()) {
                main.setScreen(screens.get("homeScreen"));
            }
            return true;
        });

        retryButton.addListener(event -> {
            if (retryButton.isPressed()) {
                main.setScreen(screens.get("Level1Screen"));
            }
            return true;
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        batch.dispose();
        ExitTexture.dispose();
        retryTexture.dispose();
        stage.dispose();
    }
}
