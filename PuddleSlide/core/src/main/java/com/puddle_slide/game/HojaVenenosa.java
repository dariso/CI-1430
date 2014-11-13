package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Meli on 12/11/2014.
 */
public class HojaVenenosa {

    private Body hojaVenenosaBody;
    private Vector2 puntoRef;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    public HojaVenenosa(World world, float x, float y, float ancho, float alto) {
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/hojaVenenosa.json"));

        BodyDef hojaVenenosaDef = new BodyDef();
        hojaVenenosaDef.type = BodyDef.BodyType.DynamicBody;
        hojaVenenosaDef.position.set(x, y);
        hojaVenenosaBody = world.createBody(hojaVenenosaDef);

        PolygonShape polygonShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_HOJAVENENOSA;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_BORDE;
        fixtureDef.density = 1000f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.42f;

        bodyEditorLoader.attachFixture( hojaVenenosaBody,"hojaVenenosa",  fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("hojaVenenosa", ancho * WORLD_TO_BOX);
    }

    public float getX(){
        return this.hojaVenenosaBody.getPosition().x * BOX_TO_WORLD;
    }

    public float getY(){
        return this.hojaVenenosaBody.getPosition().y * BOX_TO_WORLD;
    }

    public float getAngulo(){
        return this.hojaVenenosaBody.getAngle();
    }

    public Vector2 getOrigen(){
        return puntoRef;
    }

    public void mover(Vector2 vec){this.hojaVenenosaBody.applyForceToCenter(vec, true);}

    public Body getHojaVenenosaBody(){
        return this.hojaVenenosaBody;
    }

    public float getMasa(){
        return this.hojaVenenosaBody.getMass();
    }


}
