package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Meli 20/10/2014.
 */
public class Puas implements ObjetoJuego{

    private Body puasBody;
    private Vector2 puntoRef;
    private float largo, ancho;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    /**
     * Constructor de la puas
     * @param world Mundo en el que se dibujara la puas
     * @param x Posicion en el eje x en el que se dibujara la puas
     * @param y Posicion en el eje y en el que se dibujara la puas
     * @param ancho Ancho del sprite de la puas
     * @param alto Alto del sprite de la puas
     * */
    public Puas(World world, float x, float y, float ancho, float alto, boolean vuelto) {
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/puas.json"));

        largo = alto;
        this.ancho = ancho;

        BodyDef puasDef = new BodyDef();
        puasDef.type = BodyDef.BodyType.DynamicBody;
        puasDef.position.set(x, y);
        puasBody = world.createBody(puasDef);

        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_PUAS;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_BORDE | FigureId.BIT_MANZANA;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.5f;

        bodyEditorLoader.attachFixture( puasBody,"puas",  fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("puas", ancho * WORLD_TO_BOX);

        if(vuelto){
            puasBody.setTransform(x,y,180 * MathUtils.radiansToDegrees * WORLD_TO_BOX);
        }

    }

    @Override
    public float getX(){
        return puasBody.getPosition().x * BOX_TO_WORLD;
    }

    @Override
    public float getY(){
        return puasBody.getPosition().y * BOX_TO_WORLD;
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
    public float getAngulo(){
        return puasBody.getAngle();
    }

    @Override
    public Vector2 getOrigen(){
        return puntoRef;
    }

    @Override
    public float getMasa(){
        return puasBody.getMass();
    }

    public Body getPuasBody() {
        return puasBody;
    }
}
