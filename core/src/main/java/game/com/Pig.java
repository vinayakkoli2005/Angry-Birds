package game.com;

import com.badlogic.gdx.graphics.Texture;

public abstract class Pig {
    protected float health;
    protected Texture texture;
    protected float x, y;
    protected float width, height;

    public Pig(float health, Texture texture, float x, float y, float width, float height) {
        this.health = health;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public float getx() {
        return x;
    }

    public float gety() {
        return y;
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
}
