package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.List;

import static game.com.Main.saved_game;
import static game.com.Main.screens;

public class Level2Screen extends Level implements Screen {
    private Texture levelImage;
    private SpriteBatch batch;
    private TextButton pauseButton;
    private BitmapFont font, customFont;
    private Stage stage;
    private Main main;
    private TextButton win;
    private TextButton lose;

    private List<Bird> birds;
    private List<Block> blocks;
    private List<Pig> pigs;

    private Sling_Shot slingshot;

    float Red_Bird_Health = 1;
    float Big_Red_Bird_Health = 1;
    float Black_Bird_Health = 1;

    private boolean isPaused = false;

    public Level2Screen(Main main) {
        this.main = main;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        font.getData().setScale(2f);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.color = Color.WHITE;
        customFont = generator.generateFont(parameter);
        generator.dispose();


        pauseButton = new TextButton("Pause", createButtonStyle(font));
        pauseButton.setSize(150, 80);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 20, Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
        stage.addActor(pauseButton);

        win = new TextButton("WIN", createButtonStyle(font));
        win.setSize(150, 80);
        win.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 500, Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
        stage.addActor(win);

        lose = new TextButton("LOSE", createButtonStyle(font));
        lose.setSize(150, 80);
        lose.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 1000, Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
        stage.addActor(lose);


        createPauseMenu(stage);

        win.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("winButton clicked");

                main.setScreen(new Win_screen(main));
                System.out.println("You win!");
            }
        });

        lose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("loseButton clicked");

                main.setScreen(new Lose_Screen(main));
                System.out.println("You lose!");

            }
        });

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("pauseButton clicked");
                isPaused = !isPaused;
                togglePauseMenu(isPaused);
            }
        });


        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("resumeButton clicked");
                isPaused = false;
                togglePauseMenu(false);
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restartButton clicked");
                screens.replace("Level1Screen", new Level1Screen(main));
                main.setScreen(screens.get("Level1Screen"));
            }
        });

        save_exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (screens.get("Level1Screen") != null) {
                    screens.remove("Level1Screen");
                    screens.put("Level1Screen", new Level1Screen(main));
                    saved_game.put("Level1Screen", new Level1Screen(main));
                }
                System.out.println("Game Saved!");

                main.setScreen(new Game_saved(main));
            }
        });

        initGameObjects();
    }


    private void initGameObjects() {
        birds = new ArrayList<>();
        blocks = new ArrayList<>();
        pigs = new ArrayList<>();

        levelImage = new Texture(Gdx.files.internal("images/level2.png"));

        Texture slingshotTexture = new Texture(Gdx.files.internal("images/Sling_Shot.png"));
        slingshot = new Sling_Shot(50, 50,slingshotTexture , 100, 100);
        // Add birds
        birds.add(new Red_Bird(70, 70, new Texture(Gdx.files.internal("images/Red_Bird.png")), 40, 40, Red_Bird_Health)); // Position near slingshot

      // Add pigs
        blocks.add(new Wood_Block(1200, 200, new Texture(Gdx.files.internal("images/vertical_wood_block.png")), 40, 300,1));
        blocks.add(new Wood_Block(1200, 200, new Texture(Gdx.files.internal("images/vertical_wood_block.png")), 40, 300,1));

        // Horizontal Wooden Blocks
        blocks.add(new Wood_Block(950, 170, new Texture(Gdx.files.internal("images/horizontal_wood_block.png")), 150, 40,1));  // Bottom
        blocks.add(new Wood_Block(950, 370, new Texture(Gdx.files.internal("images/horizontal_wood_block.png")), 150, 40,1));  // Middle
        blocks.add(new Wood_Block(950, 450, new Texture(Gdx.files.internal("images/horizontal_wood_block.png")), 150, 40,1));  // Top

        // Ice Blocks
        blocks.add(new Ice_Block(930, 170, new Texture(Gdx.files.internal("images/vertical_ice_block.png")), 40, 100,1));  // Left Ice Block
        blocks.add(new Ice_Block(1000, 170, new Texture(Gdx.files.internal("images/vertical_ice_block.png")), 40, 100,1)); // Right Ice Block

        // Add pigs
        pigs.add(new PIG_1(970, 460, new Texture(Gdx.files.internal("images/PIG_1.png")), 50, 50, 1)); // Pig on top


    }



    private TextButton.TextButtonStyle createButtonStyle(BitmapFont font) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        return buttonStyle;
    }

    @Override
    public void show() {


        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(levelImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Fullscreen image
        slingshot.draw(batch);


        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.getx(), bird.gety(), bird.getWidth(), bird.getHeight());
        }


        for (Block block : blocks) {
            batch.draw(block.texture, block.x, block.y, block.width, block.height);
        }

        for (Pig pig : pigs) {
            batch.draw(pig.texture, pig.x, pig.y, pig.width, pig.height);
        }

        batch.end();

        if(!isPaused){
            stage.act(delta);
        }
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
        levelImage.dispose();
        batch.dispose();
        stage.dispose();
        slingshot.getTexture().dispose();

        for (Bird bird : birds) {
            bird.texture.dispose();
        }
        for (Block block : blocks) {
            block.texture.dispose();
        }
        for (Pig pig : pigs) {
            pig.texture.dispose();
        }
    }
}
