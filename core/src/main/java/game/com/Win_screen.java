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

public class Win_screen implements Screen {

    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Main main;

    protected ImageButton nextLevelButton, exitButton;

    private Texture ExitTexture;
    private Texture NextLevelTexture;

    private Stage stage; // To manage UI elements

    public Win_screen(Main main) {
        this.main = main;
        backgroundTexture = new Texture(Gdx.files.internal("images/win_screen.png"));
        batch = new SpriteBatch();

        ExitTexture = new Texture(Gdx.files.internal("images/exit.png"));
        NextLevelTexture = new Texture(Gdx.files.internal("images/NEXT.png"));


        exitButton = new ImageButton(new TextureRegionDrawable(ExitTexture));
        nextLevelButton = new ImageButton(new TextureRegionDrawable(NextLevelTexture));

        exitButton.setSize(200, 100);
        nextLevelButton.setSize(200, 100);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        exitButton.setPosition(100, stage.getViewport().getWorldHeight()/2);
        nextLevelButton.setPosition(stage.getViewport().getWorldWidth() - 500, stage.getViewport().getWorldHeight()/2);

        stage.addActor(exitButton);
        stage.addActor(nextLevelButton);

        exitButton.addListener(event -> {
            if (exitButton.isPressed()) {
                main.setScreen(screens.get("homeScreen"));
            }
            return true;
        });

        nextLevelButton.addListener(event -> {
            if (nextLevelButton.isPressed()) {
                main.setScreen(new Level_screen(main));
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
        NextLevelTexture.dispose();
        stage.dispose();
    }
}
