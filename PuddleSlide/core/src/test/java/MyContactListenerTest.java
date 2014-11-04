import com.puddle_slide.game.MyContactListener;
import com.puddle_slide.game.SoundControl;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
/**
 * Created by kalam on 03/11/2014.
 */


public class MyContactListenerTest {

    MyContactListener escuchadorColision;

    SoundControl sonido;
    public MyContactListenerTest() {

        escuchadorColision= MyContactListener.getInstancia(sonido);
    }

    @Test
    public void testInstanciaUnica() {
        sonido = mock(SoundControl.class);
         MyContactListener escuchador= MyContactListener.getInstancia(sonido);
        MyContactListener escuchador2 = MyContactListener.getInstancia(sonido);
        assertSame(escuchador,escuchador2);
        // En este metodo usted debe revisar que aun y cuando se pida muchas veces el objeto escuchador de colisiones
        // la referencia sigue siendo la misma.
    }
}
