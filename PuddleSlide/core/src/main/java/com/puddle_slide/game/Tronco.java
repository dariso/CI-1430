package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by daniel on 07/10/14.
 */
public class Tronco implements ObjetoJuego{

    private Body troncoBody;
    private Vector2 puntoRef;
    private boolean esTroncoDerecho;
    private float ancho;
    private float largo;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    public Tronco(World world, float x, float y,float anch,float larg,float angulo,boolean esDer, boolean esDinamico){
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
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_TRONCO | FigureId.BIT_BORDE | FigureId.BIT_HOJABASICA | FigureId.BIT_HONGO | FigureId.BIT_MANZANA | FigureId.BIT_PUAS;
        fixtureDef.friction = 0f;
        fixtureDef.density = 1500f;
        fixtureDef.restitution = 0.1f;

        if(esDer){
            bodyEditorLoader.attachFixture(troncoBody,"troncoDer",fixtureDef,ancho * WORLD_TO_BOX);
            puntoRef = bodyEditorLoader.getOrigin("troncoDer", ancho * WORLD_TO_BOX);
        }
        else{
            bodyEditorLoader.attachFixture(troncoBody,"troncoIzq",fixtureDef,ancho * WORLD_TO_BOX);
            puntoRef = bodyEditorLoader.getOrigin("troncoIzq", ancho * WORLD_TO_BOX);
        }

        troncoBody.setTransform(troncoBody.getPosition(),angulo * MathUtils.radiansToDegrees);
    }

    @Override
    public float getX(){
        return Math.round(troncoBody.getPosition().x * BOX_TO_WORLD);
    }

    @Override
    public float getY(){
        return Math.round(troncoBody.getPosition().y * BOX_TO_WORLD);
    }

    @Override
    public float getAngulo(){
        return troncoBody.getAngle();
    }

    @Override
    public float getWidth(){return ancho;}

    @Override
    public float getHeight(){return largo;}

    @Override
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
