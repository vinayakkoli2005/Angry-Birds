package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

class Red_Bird extends Bird {
    public Red_Bird(World world, float x, float y , Texture texture, float height, float width, float damage,Level level) {
        super(world, damage, new Texture("images/Red_Bird.png"), x, y, width, height,level);
    }
    @Override
    public String getType(){
        return "Red_Bird";
    }
}
