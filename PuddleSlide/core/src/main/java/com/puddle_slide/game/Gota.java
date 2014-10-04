package com.puddle_slide.game;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
/**
 * Created by xia on 9/30/14.
 */
public class Gota {

    private Body gotaBody;

    /**
     * Constructor de la gota
     * @param world Mundo en el que se dibujara la gota
     * @param x Posicion en el eje x en el que se dibujara la gota
     * @param y Posicion en el eje y en el que se dibujara la gota
     * @param ancho Ancho del cuerpo de la gota
     * */
    public Gota(World world, float x, float y, float ancho){
        BodyDef gotaDef = new BodyDef();
        gotaDef.type = BodyDef.BodyType.DynamicBody;
        gotaDef.position.set(x, y);
        gotaBody = world.createBody(gotaDef);

        CircleShape gotaShape = new CircleShape();
        gotaShape.setRadius(ancho/2);                   //Para que sea el radio dividimos el ancho (diametro) por 2
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = gotaShape;
        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits=FigureId.BIT_GOTA;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_HOJA|FigureId.BIT_BORDE;
        fixtureDef.density = 999.97f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.9f;
        //Id del objeto
        gotaBody.createFixture(fixtureDef).setUserData("gota");
        gotaShape.dispose();

    }

    public float getX(){
        return gotaBody.getPosition().x;
    }

    public float getY(){
        return gotaBody.getPosition().y;
    }
}
