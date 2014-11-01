package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Meli on 20/10/2014.
 */
public class Puas {
    private Body puasBody;
    private Vector2 puntoRef;
    private float ancho;
    private float largo;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    public Puas(World world, float x, float y, float ancho, float alto){
        BodyEditorLoader bodyEditorLoader;
        
        bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/puas.json"));

        BodyDef puasDef = new BodyDef();
        puasDef.type = BodyDef.BodyType.StaticBody;
        puasDef.position.set(x,y);
        puasBody = world.createBody(puasDef);

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_PUAS;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA|FigureId.BIT_MANZANA;
        fixtureDef.friction = 0f;

        bodyEditorLoader.attachFixture(puasBody,"puas",fixtureDef,ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("puas", ancho * WORLD_TO_BOX);

    }

    public float getX(){
        return puasBody.getPosition().x * BOX_TO_WORLD;
    }

    public float getY(){
        return puasBody.getPosition().y * BOX_TO_WORLD;
    }

    public float getAngulo(){
        return puasBody.getAngle();
    }

    public Vector2 getOrigen(){
        return puntoRef;
    }

    public float getMasa() { return puasBody.getMass(); }
}
