package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by xia on 9/30/14.
 */
public class Gota {

    private Body gotaBody;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;
    private Vector2 puntoRef;


    /**
     * Constructor de la gota
     * @param world Mundo en el que se dibujara la gota
     * @param x Posicion en el eje x en el que se dibujara la gota
     * @param y Posicion en el eje y en el que se dibujara la gota
     * @param ancho Ancho del cuerpo de la gota
     * */
    public Gota(World world, float x, float y, float ancho){
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/gota.json"));

        BodyDef gotaDef = new BodyDef();
        gotaDef.type = BodyDef.BodyType.DynamicBody;
        gotaDef.position.set(x, y);
        gotaBody = world.createBody(gotaDef);

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_GOTA;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_HOJA|FigureId.BIT_BORDE|FigureId.BIT_TRONCO|FigureId.BIT_MANZANA|FigureId.BIT_HONGO;
        fixtureDef.density = 999.97f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.7f;

        bodyEditorLoader.attachFixture(gotaBody, "gota", fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("gota", ancho *  WORLD_TO_BOX);
    }

    public void setRestitucion(float rest){
        //devuelve todas las fixtures de un body
        //La lista NO debe cambiarse
        Array<Fixture> fixtures = gotaBody.getFixtureList();
        fixtures.first().setRestitution(rest);
    }

    public float getX(){
        return gotaBody.getPosition().x * BOX_TO_WORLD;
    }

    public float getY(){
        return gotaBody.getPosition().y * BOX_TO_WORLD;
    }

    public float getAngulo(){
        return gotaBody.getAngle();
    }

    public Vector2 getOrigen(){
        return puntoRef;
    }
}