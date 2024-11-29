package game.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static game.com.CollisionHandler.destructionQueue;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

class BirdTest {

    private World world;
    private Texture texture;
    private MockBird bird;

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, -9.8f), true);
        texture = new Texture("images/Red_Bird.png");
//        texture = mock(Texture.class);
//        bird = new MockBird(world, 10f, texture, 100f, 200f, 1.5f, 1.5f, mock(Level.class));
    }

    @Test
    void testConstructorInitialization() {
        assertEquals(100f / 100f, bird.getX(), 0.01, "Bird X position should be initialized correctly.");
        assertEquals(200f / 100f, bird.getY(), 0.01, "Bird Y position should be initialized correctly.");
        assertEquals(1.5f, bird.getWidth(), "Bird width should be initialized correctly.");
        assertEquals(1.5f, bird.getHeight(), "Bird height should be initialized correctly.");
        assertEquals(10f, bird.getDamage(), "Bird damage should be initialized correctly.");
        assertNotNull(bird.getBody(), "Bird body should be initialized.");
        assertEquals(texture, bird.texture, "Bird texture should be initialized correctly.");
    }

    @Test
    void testGetXAndY() {
        assertEquals(100f / 100f, bird.getX(), 0.01, "Bird X position should be retrieved correctly.");
        assertEquals(200f / 100f, bird.getY(), 0.01, "Bird Y position should be retrieved correctly.");
    }

//    @Test
//    void testDestroy() {
//        bird.destroy();
//
//        // Simulate the destruction queue to ensure the body is marked for destruction
//        assertDoesNotThrow(() -> {
//            Timer.instance().update(1.1f); // Fast-forward the timer
//            assertTrue(destructionQueue.size > 0, "Destruction queue should contain a task.");
//        });
//    }

    @Test
    void testGetType() {
        assertEquals("MockBird", bird.getType(), "MockBird type should return 'MockBird'.");
    }

    @Test
    void testDispose() {
        bird.dispose();
//        verify(texture, never()).dispose();
    }
    class MockBird extends Bird {
        public MockBird(World world, float damage, Texture texture, float x, float y, float width, float height, Level level) {
            super(world, damage, texture, x, y, width, height, level);
        }

        @Override
        public String getType() {
            return "MockBird";
        }

        @Override
        public void update() {
        }

        @Override
        public void dispose() {
        }

        @Override
        public void destroy() {
        }
    }

}
