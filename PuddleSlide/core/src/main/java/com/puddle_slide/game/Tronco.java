package com.puddle_slide.game;

import com.badlogic.gdx.math.Vector2;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by daniel on 07/10/14.
 */
public class Tronco{

    private Body troncoBody;
    private Vector2 puntoRef;
    private boolean esTroncoDerecho;
    private float ancho;
    private float largo;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    public Tronco(World world, float x, float y,float anch,float larg,float angulo,boolean esDer){
        BodyEditorLoader bodyEditorLoader;
        ancho = anch;
        largo = larg;
        esTroncoDerecho = esDer;
        if(esDer){
            bodyEditorLoader =
                    new BodyEditorLoader(Gdx.files.internal("Shapes/troncoDer.json"));
        }
        else{
            bodyEditorLoader =
                    new BodyEditorLoader(Gdx.files.internal("Shapes/troncoIzq.json"));
        }
        BodyDef troncoDef = new BodyDef();
        troncoDef.type = BodyDef.BodyType.StaticBody;
        troncoDef.position.set(x,y);
        troncoBody = world.createBody(troncoDef);

        FixtureDef fixtureDef = new FixtureDef();

         //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_TRONCO;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA;
        fixtureDef.friction = 0f;
        if(esDer){
            bodyEditorLoader.attachFixture(troncoBody,"troncoDer",fixtureDef,ancho * WORLD_TO_BOX);
            puntoRef = bodyEditorLoader.getOrigin("troncoDer", ancho * WORLD_TO_BOX);

        }
        else{
            bodyEditorLoader.attachFixture(troncoBody,"troncoIzq",fixtureDef,ancho * WORLD_TO_BOX);
            puntoRef = bodyEditorLoader.getOrigin("troncoIzq", ancho * WORLD_TO_BOX);
        }

        troncoBody.setTransform(troncoBody.getPosition(),angulo);
    }

    public float getX(){
        return troncoBody.getPosition().x * BOX_TO_WORLD;
    }

    public float getY(){
        return troncoBody.getPosition().y * BOX_TO_WORLD;
    }

    public float getAngulo(){
        return troncoBody.getAngle();
    }

    public float getWidth(){return ancho;}

    public float getHeight(){return largo;}

    public Vector2 getOrigen(){
        return puntoRef;
    }

    public Body getTroncoBody() {
        return troncoBody;
    }
}
