

package game.com;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static game.com.Main.saved_game;
import static game.com.Main.screens;

public class Level2Screen extends Level implements  Screen {

    private Texture levelImage;
    private SpriteBatch batch;
    private ImageButton pauseButton;
    private BitmapFont font, customFont;
    private Stage stage;
    private static Main main;
    private TextButton win;
    private TextButton lose;

    private Sling_Shot slingshot;

    private boolean isPaused = false;

    private Body groundBody;

    private Body testbody;
    private Level2Screen level2;

    private Sling_Shot sling;
    private Screen win_screen;

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

        Texture pauseTexture = new Texture(Gdx.files.internal("images/pause.png"));
        pauseButton = new ImageButton(new TextureRegionDrawable(pauseTexture));
        pauseButton.setSize(150, 100);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 20, Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
        stage.addActor(pauseButton);

        win_screen = new Win_screen(main);

        win = new TextButton("WIN", createButtonStyle(font));
        win.setSize(150, 80);
        win.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 500, Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
        stage.addActor(win);

        lose = new TextButton("LOSE", createButtonStyle(font));
        lose.setSize(150, 80);
        lose.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 1000, Gdx.graphics.getHeight() - pauseButton.getHeight() - 20);
        stage.addActor(lose);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        createPauseMenu(stage);
        this.level2=this;

        win.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("winButton clicked");
                saved_game.remove("Level1Screen");
                main.setScreen(new Win_screen(main));
                System.out.println("You win!");

            }
        });

        lose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("loseButton clicked");
                saved_game.remove("Level1Screen");
                main.setScreen(new Lose_Screen(main,level2));


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
                dispose();
                System.out.println("restartButton clicked");

                main.setScreen(new Level2Screen(main));
            }
        });

        save_exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

//                try {
//                    saveGame();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }


                System.out.println("Game Saved!");

                main.setScreen(new Game_saved(main));


            }
        });

        createGround();
        createRightWall();
        initGameObjects();

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(sling);
        Gdx.input.setInputProcessor(inputMultiplexer);
        batch = new SpriteBatch();

        world.setContactListener(new CollisionHandler(sling,main,this));

    }



    private void createRightWall() {

        BodyDef wallBodyDef = new BodyDef();
        wallBodyDef.position.set(6000 / 100f, 275 / 100f);
        wallBodyDef.type = BodyDef.BodyType.StaticBody;
        Body wallBody = world.createBody(wallBodyDef);

        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(1 / 100f, 550 );

        FixtureDef wallFixtureDef = new FixtureDef();
        wallFixtureDef.shape = wallShape;
        wallFixtureDef.friction = 0.9f;
        wallFixtureDef.restitution = 0.1f;

        wallBody.createFixture(wallFixtureDef);

        wallShape.dispose();
    }

    private void createGround() {

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(3000/100f,275/100f);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBody = world.createBody(groundBodyDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(3000/100f,275/100f);

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.9f;
        groundFixtureDef.restitution = 0.1f;

        groundBody.createFixture(groundFixtureDef);

        groundShape.dispose();

    }


    private void initGameObjects() {
        birds = new ArrayList<>();
        blocks = new ArrayList<>();
        pigs = new ArrayList<>();

        levelImage = new Texture(Gdx.files.internal("images/level1.png"));

        Texture slingshotTexture = new Texture(Gdx.files.internal("images/Sling_Shot.png"));

        birds.add(new Red_Bird(world,240, 1000, new Texture(Gdx.files.internal("images/Red_Bird.png")), 80, 80, 5,this)); // Position near slingshot
        birds.add(new Big_Red_Bird(world,280, 1000, new Texture(Gdx.files.internal("images/Big_Red_Bird.png")), 100, 100, 6,this)); // Position near slingshot
        birds.add(new Black_Bird(world,320, 1000, new Texture(Gdx.files.internal("images/Black_Bird.png")), 80, 80, 7,this)); // Position near slingshot

        blocks.add(new Wood_Block(world,3500, 1000, new Texture(Gdx.files.internal("images/vertical_wood_block.png")), 70, 400,5,this));  // Left Wood Block

        blocks.add(new Rock_Block(world,4500, 1000, new Texture(Gdx.files.internal("images/vertical_rock_block.png")), 70, 400,7,this));  // Left Rock Block

        blocks.add(new Ice_Block(world,4000, 1400, new Texture(Gdx.files.internal("images/horizontal_ice_block.png")), 700, 50,4,this)); // Right Ice Block

        pigs.add(new PIG_2(world, 4000, 1450, new Texture(Gdx.files.internal("images/PIG_2.png")), 80, 80, 6,this)); // Pig 2
        pigs.add(new PIG_1(world, 4000, 1000, new Texture(Gdx.files.internal("images/PIG_1.png")), 80, 80, 5,this)); // Pig 2

        this.sling = new Sling_Shot(130, 180,slingshotTexture , 300, 300,world,this,main);

        sling.selectedBird();

    }
    public static void setwin(){
        main.setScreen(new Win_screen(main));
    }

    @Override
    public void setlose(){
        main.setScreen(new Lose_Screen(main,this));
    }


    private TextButton.TextButtonStyle createButtonStyle(BitmapFont font) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        return buttonStyle;
    }


    @Override
    public void show() {


    }
    public InputMultiplexer getmultiplexer(){
        return inputMultiplexer;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(levelImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sling.draw(batch);




        for (Pig pig : pigs) {
            float BodyX = pig.getX() * 30f;
            float BodyY = pig.getY() * 30f;
            float r = 50;
            batch.draw(pig.texture, BodyX - r +80, BodyY - r + 40,r * 2, r * 2);
        }

        for (Bird bird : birds) {
            float bodyx = bird.getX() * 30f;
            float bodyy = bird.getY() * 30f;
            float r = 50;
            batch.draw(bird.texture, bodyx - r+15, bodyy - r+15, r * 2, r * 2);
        }

        for (Block block : blocks) {
            float bodyX = block.getX() *30f;
            float bodyY = block.getY() * 30f;
            float blockWidth = block.getWidth() * 0.75f;
            float blockHeight = block.getHeight() * 0.75f;
            block.body.setTransform(block.getX(), block.getY(), block.body.getAngle());
            batch.draw(block.textureRegion, bodyX - blockWidth /2+ 90, bodyY - blockHeight/2+  33, blockWidth/2,blockHeight/2,blockWidth,blockHeight,1,1,(float)Math.toDegrees(block.body.getAngle()));
        }


        world.step(1 / 60f, 6, 2);
        CollisionHandler.processPendingTasks();
//        debugRenderer.render(world, camera.combined);


        stage.draw();
        batch.end();

    }
    @Override
    public void winGame() {
        win.setVisible(true);
        win.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(win_screen);  // Transition to Win Screen
            }
        });


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                main.setScreen(new Level3Screen(main));  // Transition to Level 2 Screen after 5 seconds
            }
        }, 5);
    }

    // Method to handle losing the game
    @Override
    public void loseGame() {
        lose.setVisible(true);
        lose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new Lose_Screen(main,level2));  // Transition to Lose Screen
            }
        });
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
        sling.dispose();


        for (Bird bird : birds) {
            world.destroyBody(bird.getBody());
        }
        birds.clear();

        for (Block block : blocks) {
            world.destroyBody(block.getBody());
        }
        blocks.clear();

        for (Pig pig : pigs) {
            world.destroyBody(pig.getBody());
        }
        pigs.clear();


    }
}

