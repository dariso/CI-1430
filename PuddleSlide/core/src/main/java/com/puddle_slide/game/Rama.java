package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by xia on 11/21/14.
 */
public class Rama implements ObjetoJuego{
    private Body ramaBody;
    private Vector2 puntoRef;
    private int tipo;               //El cuerpo a cargar
    private float ancho;
    private float largo;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    public Rama(World world, float x, float y,float anch,float larg, int t){
        BodyEditorLoader bodyEditorLoader;
        ancho = anch;
        largo = larg;
        tipo = t;

        bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/ramas.json"));

        BodyDef ramaDef = new BodyDef();
        ramaDef.type = BodyDef.BodyType.StaticBody;
        ramaDef.position.set(x, y);
        ramaBody = world.createBody(ramaDef);

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_RAMA;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_BORDE | FigureId.BIT_HOJABASICA | FigureId.BIT_MANZANA;
        fixtureDef.friction = 0f;
        fixtureDef.density = 1500f;
        fixtureDef.restitution = 0.1f;

        //¡Que feo!
        switch (tipo) {
            //Rama izquierda normal
            case 1:
                bodyEditorLoader.attachFixture(ramaBody, "ramaIzquierda", fixtureDef, ancho * WORLD_TO_BOX);
                puntoRef = bodyEditorLoader.getOrigin("ramaIzquierda", ancho * WORLD_TO_BOX);
                break;
            //Rama izquierda viendo para abajo
            case 2:
                bodyEditorLoader.attachFixture(ramaBody, "ramaIzquierdaAbajo", fixtureDef, ancho * WORLD_TO_BOX);
                puntoRef = bodyEditorLoader.getOrigin("ramaIzquierdaAbajo", ancho * WORLD_TO_BOX);
                break;

            //Rama derecha normal
            case 3:
                bodyEditorLoader.attachFixture(ramaBody,"ramaDerecha",fixtureDef,ancho * WORLD_TO_BOX);
                puntoRef = bodyEditorLoader.getOrigin("ramaDerecha", ancho * WORLD_TO_BOX);
                break;

            //Rama derecha viendo para abajo
            case 4:
                bodyEditorLoader.attachFixture(ramaBody,"ramaDerechaAbajo",fixtureDef,ancho * WORLD_TO_BOX);
                puntoRef = bodyEditorLoader.getOrigin("ramaDerechaAbajo", ancho * WORLD_TO_BOX);
                break;
        }

    }

    @Override
    public float getX(){
        return ramaBody.getPosition().x * BOX_TO_WORLD;
    }

    @Override
    public float getY(){
        return ramaBody.getPosition().y * BOX_TO_WORLD;
    }

    @Override
    public float getAngulo(){
        return ramaBody.getAngle();
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
        return ramaBody.getMass();
    }

    public Body getRamaBody() {
        return ramaBody;
    }
}
