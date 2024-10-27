package game.com;

import com.badlogic.gdx.graphics.Texture;

class Red_Bird extends Bird {
    public Red_Bird(float x, float y ,Texture texture, float height, float width, float health) {
        super(health, new Texture("images/Red_Bird.png"), x, y, width, height);
    }
}
