package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by xia on 11/24/14.
 */
public class TroncoQuebradizo implements ObjetoJuego {
    private Body troncoBody;
    private Vector2 puntoRef;
    private float ancho;
    private float largo;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    public TroncoQuebradizo(World world, float x, float y,float anch,float larg,float angulo, boolean esDinamico){
        BodyEditorLoader bodyEditorLoader;
        ancho = anch;
        largo = larg;

        bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/troncoQuebradizo.json"));

        if(esDinamico) {
            BodyDef troncoDef = new BodyDef();
            troncoDef.type = BodyDef.BodyType.DynamicBody;
            troncoDef.position.set(x, y);
            troncoBody = world.createBody(troncoDef);
        }else{
            BodyDef troncoDef = new BodyDef();
            troncoDef.type = BodyDef.BodyType.StaticBody;
            troncoDef.position.set(x, y);
            troncoBody = world.createBody(troncoDef);
        }

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_TRONCO;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA|FigureId.BIT_TRONCO|FigureId.BIT_BORDE|FigureId.BIT_HOJABASICA|FigureId.BIT_HONGO|FigureId.BIT_MANZANA|FigureId.BIT_PUAS;
        fixtureDef.friction = 0f;
        fixtureDef.density = 1500f;
        fixtureDef.restitution = 0.1f;

        bodyEditorLoader.attachFixture(troncoBody,"troncoQuebradizo",fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("troncoQuebradizo", ancho * WORLD_TO_BOX);

        troncoBody.setTransform(troncoBody.getPosition(),angulo * MathUtils.radiansToDegrees);
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

    @Override
    public float getMasa() {
        return troncoBody.getMass();
    }

    public Body getTroncoBody() {
        return troncoBody;
    }
}
