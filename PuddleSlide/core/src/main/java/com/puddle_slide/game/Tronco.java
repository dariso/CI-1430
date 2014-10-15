package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by daniel on 07/10/14.
 */
public class Tronco {

    private Body troncoBody;
    private Vector2 puntoRef;
    private boolean esTroncoDerecho;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    //direccion, 1 = orientado derecha,0 izquierda
    public Tronco(World world, float x, float y,float ancho,float largo,float angulo,boolean esDer){

        esTroncoDerecho = esDer;
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/tronco.json"));
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
            bodyEditorLoader.attachFixture(troncoBody,"tronco1",fixtureDef,ancho * WORLD_TO_BOX);
            puntoRef = bodyEditorLoader.getOrigin("tronco1", ancho * WORLD_TO_BOX);

        }
        else{
            bodyEditorLoader.attachFixture(troncoBody,"tronco",fixtureDef,ancho * WORLD_TO_BOX);
            puntoRef = bodyEditorLoader.getOrigin("tronco", ancho * WORLD_TO_BOX);
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

    public Vector2 getOrigen(){
        return puntoRef;
    }
}
