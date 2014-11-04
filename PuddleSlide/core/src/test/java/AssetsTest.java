import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.puddle_slide.game.Puddle_Slide;

import org.junit.Test;

import static org.junit.Assert.*;

public class AssetsTest{

    //setup

    @Test
    public void testAssets(){
        //setup
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication app = new HeadlessApplication(new Puddle_Slide(),config);

        //prueba
         System.out.print("Hola");
        /*
        assertTrue(Gdx.files.internal("gotty.png").exists());
        assertTrue(Gdx.files.internal("gottyCS.png").exists());
        assertTrue(Gdx.files.internal("gotty2.png").exists());
        assertTrue(Gdx.files.internal("hoja2.png").exists());
        assertTrue(Gdx.files.internal("background.png").exists());
        assertTrue(Gdx.files.internal("drop.wav").exists());
        assertTrue(Gdx.files.internal("boinki.wav").exists());
        assertTrue(Gdx.files.internal("branch.mp3").exists());
        assertTrue(Gdx.files.internal("hongosNaranja.png").exists());

        assertTrue(Gdx.files.internal("Shapes/troncoDer.json").exists());
        assertTrue(Gdx.files.internal("Shapes/troncoIzq.json").exists());
        assertTrue(Gdx.files.internal("Shapes/manzana.json").exists());
        assertTrue(Gdx.files.internal("Shapes/hoja.json").exists());
        assertTrue(Gdx.files.internal("Shapes/hongo.json").exists());
    */
        assertTrue(Gdx.files.internal("gotty.png").exists());
      //  assertTrue(Gdx.files.internal("Shapes/troncoDer.json").exists());
    }

}
