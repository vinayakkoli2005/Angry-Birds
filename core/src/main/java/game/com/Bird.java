package game.com;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import static game.com.CollisionHandler.destructionQueue;
public class Bird {
    protected float Damage;
    protected Texture texture;
    protected float x, y;
    protected float width, height;
    protected Body body;
    World world;
    Level level;

    public Bird(World world,float damage, Texture texture, float x, float y, float width, float height,Level level) {
        this.Damage = damage;
        this.texture = texture;
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
        bodyDef.position.set(x/100f,y/100f);

        body = world.createBody(bodyDef);

        body.setUserData(this);


        CircleShape shape = new CircleShape();
        shape.setRadius(130 / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        shape.dispose();

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

    public float getDamage() {
        return Damage;
    }

    public void update() {

    }
    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }



    public void destroy() {
        if (body != null && world != null) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    destructionQueue.add(() -> world.destroyBody(body));
                }
            }, 1); // Delay of 1 second
        }
    }
    public String getType(){
        return "Bird";
    }

}


