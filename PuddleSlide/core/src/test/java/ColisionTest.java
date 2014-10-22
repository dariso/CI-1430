import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.puddle_slide.game.Gota;
import com.puddle_slide.game.Hongo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**|
 * Created by kalam on 14/10/2014.
 */
public class ColisionTest {
    World world;
    Hongo hongo;
    Gota enki;
    @Before
    public void antesTest(){
        world = new World(new Vector2(0, -9.8f), true);

        hongo= new Hongo(world, 1, 1, 2, 2);

        enki = new Gota(world, 1, 1, 2);
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    }
    @Test
    public void testColision() {
        //setup



        System.out.print("Prueba");
        assertNotNull( hongo);
        //prueba }
    }

    }

