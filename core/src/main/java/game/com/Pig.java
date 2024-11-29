package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Pig {
    protected float health;
    protected Texture texture;
    protected float x, y;
    protected float width, height;
    protected Body body;
    World world;
    Level level;


    public Pig(World world,float health, Texture texture, float x, float y, float width, float height,Level level) {
        this.health = health;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
        this.level = level;
        createPhysicsBody(world, x, y);
    }

    public String gettype(){
        return "Pig";
    }
    private void createPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f);

        body = world.createBody(bodyDef);

        body.setUserData(this);

        PolygonShape shape = new PolygonShape();

        float width = 1.3f;
        float height = 1.3f;
        shape.setAsBox((float)(width/1.5) , (float)(height/1.5) );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public float getX() {
        return body.getPosition().x; // Retrieve x-coordinate from physics body
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
    public void update() {

    }
    public void dispose() {
        // Clean up resources such as texture
        texture.dispose();
    }
    public Body getBody(){
        return body;
    }
}


