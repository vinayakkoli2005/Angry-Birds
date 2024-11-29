package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

class PIG_1 extends Pig {
    public PIG_1(World world , float x, float y, Texture texture, float width, float height, float health,Level level){
        super(world ,health, texture, x, y, width, height,level);
    }
    @Override
    public String gettype(){
        return "PIG_1";
    }
}
