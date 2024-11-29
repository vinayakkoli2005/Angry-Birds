package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

class Wood_Block extends Block {
    public Wood_Block(World world , float x, float y , Texture texture, float width, float height, float health,Level level) {
        super(world ,health, texture, x, y, width, height,level);
    }
    @Override
    public String gettype(){
        return "Wood_Block";
    }
}












