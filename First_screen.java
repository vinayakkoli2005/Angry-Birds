package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import static game.com.Main.screens;

public class First_screen implements Screen {
    private Stage stage;
    private Texture image;
    private SpriteBatch batch;
    private Image imgActor;
    private Viewport viewport;
    private BitmapFont font;
    private Main main;


    private float elapsedTime; // Track elapsed time for transition

    public First_screen(Main main) {
        this.main = main;
        viewport = new FitViewport(1600, 900);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3);

        screens.put("First_screen", this);
    }

    @Override
    public void show() {
        image = new Texture(Gdx.files.internal("images/angry_bird.png"));
        imgActor = new Image(image);

        imgActor.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        imgActor.setPosition(0, 0);

        stage = new Stage(viewport, batch);
        stage.addActor(imgActor);

        elapsedTime = 0;
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        elapsedTime += delta;

        if (elapsedTime >= 2f && main.getScreen() == this) {
            screens.put("homeScreen", new Home_screen(main));
            main.setScreen(screens.get("homeScreen"));
        }

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
        font.dispose();
    }
}
