package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;


class Black_Bird extends Bird {
    public Black_Bird(World world, float x, float y, Texture texture, float width, float height, float damage,Level level) {
        super(world,damage, texture, x, y, width, height,level);
    }
    @Override
    public String getType(){
        return "Black_Bird";
    }
}
