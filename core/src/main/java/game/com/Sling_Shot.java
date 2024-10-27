package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sling_Shot {
    private float x;
    private float y;
    private Texture texture;
    private int width;
    private int height;

    public Sling_Shot(float x, float y, Texture texture, int width, int height) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }


}
