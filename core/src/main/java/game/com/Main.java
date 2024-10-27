package game.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Graphics.DisplayMode;

import java.util.HashMap;

public class Main extends Game {
    private Screen firstScreen;
    private Screen homeScreen;
    private float elapsedTime;
    private static Main instance;


    private SpriteBatch batch;

    public static HashMap<String,Screen> screens = new HashMap<String, Screen>();
    public static HashMap<String, Level> saved_game = new HashMap<String, Level>();

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    @Override
    public void create() {
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(displayMode);



        batch = new SpriteBatch();

        firstScreen = new First_screen(this);
        screens.put("firstScreen", firstScreen);


        setScreen(firstScreen);

        elapsedTime = 0;
    }

    @Override
    public void render() {
        super.render();


    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        if (firstScreen != null) firstScreen.dispose();
        if (homeScreen != null) homeScreen.dispose();

        if (batch != null) batch.dispose();
    }
}
