package game.com;

import com.badlogic.gdx.graphics.Texture;

class Ice_Block extends Block {
    public Ice_Block(float x, float y, Texture texture, float width, float height, float health) {
        super(health, texture, x, y, width,height);
    }
}
