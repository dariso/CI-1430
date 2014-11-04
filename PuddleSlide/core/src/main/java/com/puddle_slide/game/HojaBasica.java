package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;



/**
 * Created by xia on 9/30/14.
 */
public class HojaBasica {

    private Body hojaBody;
    private Vector2 puntoRef;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    /**
     * Constructor de la hoja
     * @param world Mundo en el que se dibujara la hoja
     * @param x Posicion en el eje x en el que se dibujara la hoja
     * @param y Posicion en el eje y en el que se dibujara la hoja
     * @param ancho Ancho del sprite de la hoja
     * @param alto Alto del sprite de la hoja
     * */
    public HojaBasica(World world, float x, float y, float ancho, float alto) {
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/hoja2.json"));

        BodyDef hojaDef = new BodyDef();
        hojaDef.type = BodyDef.BodyType.DynamicBody;
        hojaDef.position.set(x, y);
        hojaBody = world.createBody(hojaDef);

        PolygonShape polygonShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_HOJABASICA;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_BORDE;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.5f;

        bodyEditorLoader.attachFixture( hojaBody,"hoja",  fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("hoja", ancho * WORLD_TO_BOX);

    }

    public float getX(){
        return hojaBody.getPosition().x * BOX_TO_WORLD;
    }

    public float getY(){
        return hojaBody.getPosition().y * BOX_TO_WORLD;
    }

    public float getAngulo(){
        return hojaBody.getAngle();
    }

    public Vector2 getOrigen(){
        return puntoRef;
    }

    public void mover(Vector2 v){
        hojaBody.applyForceToCenter(v, true);
    }

    public Body getHojaBody(){
        return this.hojaBody;
    }
    public float getMasa(){
        return hojaBody.getMass();
    }


}
