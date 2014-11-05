import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.puddle_slide.game.MainMenuScreen;
import com.puddle_slide.game.Puddle_Slide;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by kalam on 15/10/2014.
 */
public class PantallaTest {
    //setup

    MainMenuScreen main;
    @Before
    public void antesTest(){
        main = new MainMenuScreen(new Puddle_Slide());
    }

    @Test
    public void testPantalla(){
        //setup
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication app = new HeadlessApplication(new Puddle_Slide(),config);

        //prueba
        assertNotNull(main);

    }

}
