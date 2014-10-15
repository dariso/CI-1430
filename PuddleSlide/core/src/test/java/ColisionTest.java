import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.puddle_slide.game.Puddle_Slide;

import org.junit.Test;

/**|
 * Created by kalam on 14/10/2014.
 */
public class ColisionTest {
    @Test
    public void testColision(){
        //setup
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication app = new HeadlessApplication(new Puddle_Slide(),config);

        //prueba


    }

}
