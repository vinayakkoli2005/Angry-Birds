package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

class Big_Red_Bird extends Bird {
    public Big_Red_Bird(World world,float x, float y , Texture texture, float height, float width , float damage,Level level) {
        super(world,damage, texture, x, y, width, height,level);
    }
    @Override
    public String getType(){
        return "Big_Red_Bird";
    }
}
