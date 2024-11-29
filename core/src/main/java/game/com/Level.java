package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Level  {
    protected static List<Bird> birds;
    protected static List<Block> blocks;
    protected static List<Pig> pigs;


    protected ImageButton resumeButton, restartButton, save_exitButton;

    private Texture resumeTexture;
    private Texture retryTexture;
    private Texture saveExitTexture;


    protected Stage stage;
    protected static OrthographicCamera camera;

    protected static World world;
    protected Box2DDebugRenderer debugRenderer;



    public InputMultiplexer inputMultiplexer;
    public List<Level> level;


    public Level() {

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 32f, Gdx.graphics.getHeight() / 32f);

        inputMultiplexer = new InputMultiplexer();

    }

    protected void createPauseMenu(Stage stage) {
        resumeTexture = new Texture("images/RESUME.png");
        retryTexture = new Texture("images/RETRY.png");
        saveExitTexture = new Texture("images/save.png");

        resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
        restartButton = new ImageButton(new TextureRegionDrawable(retryTexture));
        save_exitButton = new ImageButton(new TextureRegionDrawable(saveExitTexture));



        float buttonWidth = 200f;
        float buttonHeight = 80f;
        float centerX = (stage.getViewport().getWorldWidth() - buttonWidth) / 2;
        float centerY = stage.getViewport().getWorldHeight() / 2;

        resumeButton.setSize(buttonWidth, buttonHeight);
        resumeButton.setPosition(centerX, centerY + 100);

        restartButton.setSize(buttonWidth, buttonHeight);
        restartButton.setPosition(centerX, centerY);

        save_exitButton.setSize(buttonWidth, buttonHeight);
        save_exitButton.setPosition(centerX, centerY - 100);


        resumeButton.setVisible(false);
        restartButton.setVisible(false);
        save_exitButton.setVisible(false);

        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(save_exitButton);
    }

    protected void togglePauseMenu(boolean show) {
        resumeButton.setVisible(show);
        restartButton.setVisible(show);
        save_exitButton.setVisible(show);
    }

    protected void disposePauseMenu() {
        resumeTexture.dispose();
        retryTexture.dispose();
        saveExitTexture.dispose();
    }
    protected void updateWorld(float delta) {
        world.step(delta, 6, 2);
    }




    public Bird getbird1() {
        return birds.get(0);
    }

    public void loseGame(){}

    public void winGame() {
    }


    public void saveGame() throws IOException {

        ObjectMapper ObjectMapper = new ObjectMapper();

        ObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        GameData gameData = GameDataSaver();

        ObjectMapper.writeValue(new File("save.json"), gameData);


    }

    private GameData GameDataSaver() {
        GameData gameData = new GameData();
        for (Bird b : birds) {
            gameData.birdsaver.add(new BirdSaver(b.getX(), b.getY(), b.getType()));
        }
        for (Block b : blocks) {
            gameData.blocksaver.add(new BlockSaver(b.getX(), b.getY(), b.gettype(),b.getWidth(),b.getHeight(), (int) b.getHealth()));
        }
        for (Pig b : pigs) {
            gameData.pigsaver.add(new PigSaver(b.getBody().getPosition().x, b.getBody().getPosition().y, b.getClass().getSimpleName()));
        }

        return gameData;

    }


    public void loadGame(Main main) {

    }

    public void setlose() {
    }

    public static class GameData {

        public List<BirdSaver> birdsaver=new ArrayList<>();
        public List<BlockSaver> blocksaver=new ArrayList<>();
        public List<PigSaver> pigsaver=new ArrayList<>();
//        public List<Level> levels;
//
//        public GameData() {
//            levels = new ArrayList<>();
//        }
//        public void addLevel(Level level) {
//            levels.add(level);
//        }
//        public void saveLevel(int index, Level level) {
//            levels.set(index, level);
//        }

    }
    public static class BirdSaver{
        float x;
        float y;
        String BirdType;
        public BirdSaver(float x, float y, String BirdType) {
            this.x = x;
            this.y = y;
            this.BirdType = BirdType;

        }
        public BirdSaver() {
        }




    }
    public static class BlockSaver{
        float x;
        float y;
        String BlockType;
        float width;
        float height;
        int Health;
        public BlockSaver(float x, float y, String BlockType , float width, float height, int Health) {

            this.x = x;
            this.y = y;
            this.BlockType = BlockType;
            this.width = width;
            this.height = height;
            this.Health = Health;

        }
        public BlockSaver() {
        }

    }
    public static class PigSaver{
        float x;
        float y;
        String PigType;
        int Health;
        public PigSaver(float x, float y, String PigType) {
            this.x = x;
            this.y = y;
            this.PigType = PigType;
        }
        public PigSaver() {
        }

    }



}
