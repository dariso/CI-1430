import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.puddle_slide.game.Puddle_Slide;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class AssetsTest{

    //setup

    @Test
    public void testAssets(){
        //setup
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication app = new HeadlessApplication(new Puddle_Slide(),config);

        //prueba

        assertTrue(Gdx.files.internal("gotty.png").exists());
        assertTrue(Gdx.files.internal("gottyCS.png").exists());
        assertTrue(Gdx.files.internal("gotty2.png").exists());
        assertTrue(Gdx.files.internal("hoja2.png").exists());
        assertTrue(Gdx.files.internal("background.png").exists());
        assertTrue(Gdx.files.internal("drop.wav").exists());
        assertTrue(Gdx.files.internal("rain.mp3").exists());
    }

}
