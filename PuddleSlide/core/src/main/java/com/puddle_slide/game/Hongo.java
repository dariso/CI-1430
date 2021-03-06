package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by xia on 10/14/14.
 */
public class Hongo implements ObjetoJuego {

    private float largo, ancho;
    private Body hongoBody;
    private Vector2 puntoRef;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    /**
     * Constructor del hongo
     *
     * @param world Mundo en el que se dibujara la hongo
     * @param x     Posicion en el eje x en el que se dibujara el hongo
     * @param y     Posicion en el eje y en el que se dibujara el hongo
     * @param ancho Ancho del sprite del hongo
     * @param alto  Alto del sprite del hongo
     */
    public Hongo(World world, float x, float y, float ancho, float alto, boolean pequeño) {
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/hongo.json"));

        BodyDef hongoDef = new BodyDef();
        hongoDef.type = BodyDef.BodyType.DynamicBody;
        hongoDef.position.set(x, y);
        hongoBody = world.createBody(hongoDef);

        this.ancho = ancho;
        largo = alto;

        PolygonShape polygonShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_HONGO;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_BORDE | FigureId.BIT_TRONCO;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.5f;

        if (pequeño) {
            bodyEditorLoader.attachFixture(hongoBody, "hongoPequeño", fixtureDef, ancho * WORLD_TO_BOX);
        } else {
            bodyEditorLoader.attachFixture(hongoBody, "hongo", fixtureDef, ancho * WORLD_TO_BOX);
        }


        puntoRef = bodyEditorLoader.getOrigin("hongo", ancho * WORLD_TO_BOX);

    }

    @Override
    public float getX() {
        return hongoBody.getPosition().x * BOX_TO_WORLD;
    }

    @Override
    public float getY() {
        return hongoBody.getPosition().y * BOX_TO_WORLD;
    }

    @Override
    public float getWidth() {
        return ancho;
    }

    @Override
    public float getHeight() {
        return largo;
    }

    @Override
    public float getAngulo() {
        return hongoBody.getAngle();
    }

    @Override
    public Vector2 getOrigen() {
        return puntoRef;
    }

    @Override
    public float getMasa() {
        return hongoBody.getMass();
    }

    public Body getHongoBody() {
        return hongoBody;
    }

}


