package com.puddle_slide.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by xia on 9/30/14.
 */
public class Hoja {

    private Body hojaBody;

    /**
     * Constructor de la hoja
     * @param world Mundo en el que se dibujara la hoja
     * @param x Posicion en el eje x en el que se dibujara la hoja
     * @param y Posicion en el eje y en el que se dibujara la hoja
     * @param ancho Ancho del sprite de la hoja
     * @param alto Alto del sprite de la hoja
     * */
    public Hoja(World world, float x, float y, float ancho, float alto){
        BodyDef hojaDef = new BodyDef();
        hojaDef.type = BodyDef.BodyType.DynamicBody;
        hojaDef.position.set(x, y);
        hojaBody = world.createBody(hojaDef);

        PolygonShape hojaShape = new PolygonShape();
        hojaShape.setAsBox(ancho-64, alto/4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hojaShape;
        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits=FigureId.BIT_HOJA;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA|FigureId.BIT_BORDE;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.5f;
        //Id del objeto
        hojaBody.createFixture(fixtureDef).setUserData("hoja");;
        hojaShape.dispose();
    }

    public float getX(){
        return hojaBody.getPosition().x;
    }

    public float getY(){
        return hojaBody.getPosition().y;
    }

    public void mover(Vector2 v){
        hojaBody.applyForceToCenter(v, true);
    }

    public float getMasa(){
        return hojaBody.getMass();
    }
}
