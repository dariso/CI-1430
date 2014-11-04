import com.puddle_slide.game.MyContactListener;

import org.junit.Test;
/**
 * Created by kalam on 03/11/2014.
 */
public class MyContactListenerTest {
    @Test
    public void testInstanciaUnica() {

        MyContactListener escuchador = MyContactListener.getInstancia();
     //   MyContactListener escuchador2 = MyContactListener.getInstancia();
      //  assertSame(escuchador,escuchador2);
        // En este metodo usted debe revisar que aun y cuando se pida muchas veces el objeto
        // la referencia sigue siendo la misma.
    }
}
