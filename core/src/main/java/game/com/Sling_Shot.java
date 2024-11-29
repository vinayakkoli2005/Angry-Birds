package game.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;


public class Sling_Shot implements InputProcessor {
    private float x;
    private float y;
    private Texture texture;
    private int width;
    private int height;

    private static World world;
    private Body slingBody;
    private Body projectileBody;
    private static MouseJoint mouseJoint;
    private static RopeJoint ropeJoint;
    private MouseJointDef mouseJointDef;
    public static Bird Selected_Bird;
    private Level level;
    private Main main;



    public Sling_Shot(float x, float y, Texture texture, int width, int height, World world, Level level, Main main) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;
        Sling_Shot.world = world;
        Gdx.input.setInputProcessor(this);
        this.main = main;

        this.level = level;

        createSling();
        createProjectile();
    }

    private void createSling() {

        BodyDef slingBodyDef = new BodyDef();
        slingBodyDef.position.set(900/100f,1100/100f);
        slingBodyDef.type = BodyDef.BodyType.StaticBody;
        slingBody = world.createBody(slingBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 0.1f);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        slingBody.createFixture(fixtureDef);
        shape.dispose();

    }

    public void selectedBird() {
        cleanupJoints();

        if(Level1Screen.birds.isEmpty() && !Level1Screen.pigs.isEmpty()) {
            if(level instanceof Level1Screen) {
                ((Level1Screen) level).dispose();
            }
            if(level instanceof Level2Screen) {
                ((Level2Screen) level).dispose();
            }
            if(level instanceof Level2Screen) {
                ((Level2Screen) level).dispose();
            }
            main.setScreen(new Lose_Screen(main,level));
        } else if (Level1Screen.pigs.isEmpty()) {
            if(level instanceof Level1Screen) {
                ((Level1Screen) level).dispose();
            }
            if(level instanceof Level2Screen) {
                ((Level2Screen) level).dispose();
            }
            if(level instanceof Level2Screen) {
                ((Level2Screen) level).dispose();
            }
            main.setScreen(new Win_screen(main));
        } else {
            Selected_Bird = level.getbird1();


            if (Selected_Bird != null) {

                Selected_Bird.body.setLinearVelocity(0, 0);
                Selected_Bird.body.setAngularVelocity(0);

                float x = slingBody.getPosition().x + 0.2f;
                float y = slingBody.getPosition().y + 1.8f;

                Selected_Bird.getBody().setType(BodyDef.BodyType.StaticBody);
                Selected_Bird.getBody().setTransform(x, y, 0);

                RopeJointDef ropeJointDef = new RopeJointDef();
                ropeJointDef.bodyA = slingBody;
                ropeJointDef.bodyB = Selected_Bird.getBody();
                ropeJointDef.localAnchorA.set(0, 0);
                ropeJointDef.localAnchorB.set(0, 0);
                ropeJointDef.maxLength = 3f;
                ropeJoint = (RopeJoint) world.createJoint(ropeJointDef);
            }

        }


    }

    private void createProjectile() {
        BodyDef projectileBodyDef = new BodyDef();
        projectileBodyDef.position.set((x + 20) / 100f, (y + 20) / 100f);
        projectileBodyDef.type = BodyDef.BodyType.DynamicBody;
        projectileBody = world.createBody(projectileBodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;
        projectileBody.createFixture(fixtureDef);
        shape.dispose();


    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch down is called");
        if (Selected_Bird == null) {
            selectedBird();
        }

        Vector3 screenCoords = new Vector3(screenX, screenY, 0);
        Vector3 worldCoords = Level1Screen.camera.unproject(screenCoords);

        Vector2 slingPosition = slingBody.getPosition();
        Vector2 touchPosition = new Vector2(worldCoords.x, worldCoords.y);
        float distanceToSling = touchPosition.dst(slingPosition);

        if (distanceToSling <= 2.0f && Selected_Bird != null) {
            Selected_Bird.body.setType(BodyDef.BodyType.DynamicBody);

            if (mouseJoint == null) {
                MouseJointDef mouseJointDef = new MouseJointDef();
                mouseJointDef.bodyA = slingBody;
                mouseJointDef.bodyB = Selected_Bird.getBody();

                Vector2 worldTouchCoords = new Vector2(worldCoords.x, worldCoords.y);
                mouseJointDef.target.set(worldTouchCoords);

                mouseJointDef.maxForce = 10000f;
                mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
            }
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (mouseJoint != null) {

            if (mouseJoint.getBodyB() != Selected_Bird.getBody()) {
                return false;
            }

            Vector3 screenCoords = new Vector3(screenX, screenY, 0);
            Vector3 worldCoords = Level1Screen.camera.unproject(screenCoords);

            Vector2 target = new Vector2(worldCoords.x, worldCoords.y);

            mouseJoint.setTarget(target);

        }
        return true;
    }


    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (mouseJoint != null) {
            if (ropeJoint != null) {
                world.destroyJoint(ropeJoint);
                ropeJoint = null;
            }

            Vector2 launchForce = mouseJoint.getReactionForce(1 / Gdx.graphics.getDeltaTime()).scl(-3);
            Selected_Bird.getBody().applyForceToCenter(launchForce, true);

            world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }

        return true;
    }


    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    public void draw(SpriteBatch batch) {

        batch.draw(texture, x, y, width, height);

    }

    public static void cleanupJoints() {
        if (mouseJoint != null) {
            world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }
        if (ropeJoint != null) {
            world.destroyJoint(ropeJoint);
            ropeJoint = null;
        }
    }

    public void dispose() {
        cleanupJoints();

        if (texture != null) {
            texture.dispose();
            texture = null;
        }

        System.out.println("Sling_Shot resources disposed.");

    }


}
