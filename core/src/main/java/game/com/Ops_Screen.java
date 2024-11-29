package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static game.com.Main.screens;

public class Ops_Screen implements Screen {

    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Main main;
    private float timer;
    private final float displayTime = 2.0f;

    public Ops_Screen(Main main) {
        this.main = main;
        backgroundTexture = new Texture(Gdx.files.internal("images/ops.png"));
        batch = new SpriteBatch();
        timer = 0;
    }

    @Override
    public void show() {
        timer = 0;
    }

    @Override
    public void render(float delta) {
        timer += delta;

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (timer >= displayTime) {
            main.setScreen(screens.get("homeScreen"));
        }
    }

    @Override
    public void resize(int width, int height) {
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
    }
}
