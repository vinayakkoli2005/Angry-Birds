package game.com;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.util.LinkedList;
import java.util.Queue;

import static game.com.Level.world;
import static game.com.Level1Screen.blocks;
import static game.com.Level1Screen.pigs;

public class Block {
    protected float health;
    protected Texture texture;
    protected TextureRegion textureRegion;
    protected float x, y;
    protected float width, height;
    protected Body body;
    World world;
    Level level;

    public Block(World world, float health, Texture texture, float x, float y, float width, float height, Level level) {
        this.health = health;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
        this.level = level;

        createPhysicsBody(world, x, y);
    }

    private void createPhysicsBody(World world, float x, float y) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 100f, height / 100f);

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();


        body = world.createBody(bodyDef);

        body.setUserData(this);

        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.1f;


        body.createFixture(fixtureDef);

        shape.dispose();

    }
    public String gettype(){
        return "Block";
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getHealth() {
        return health;
    }

    public void reduceHealth(Bird bird) {
//        health -= bird.getDamage();
//        if (health <= 0) {
//            world.destroyBody(body);
//        }

    }

    public void update() {

    }

    public void dispose() {
        // Clean up resources such as texture
        texture.dispose();
    }


    public void destroy() {
//        world.destroyBody(body);
    }

    public Body getBody() {
        return body;
    }


}
