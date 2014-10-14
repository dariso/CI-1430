package com.puddle_slide.game;

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
    private Body hojaBody;
    private Vector2 puntoRef;
    private final float WORLD_TO_BOX = 0.01f;
    private final float BOX_TO_WORLD = 100f;

    public Tronco(World world, float x, float y,float ancho,float largo,float angulo){

        BodyDef troncoDef = new BodyDef();
        troncoDef.type = BodyDef.BodyType.StaticBody;
        troncoDef.position.set(x*WORLD_TO_BOX,y*WORLD_TO_BOX);
        troncoBody = world.createBody(troncoDef);


        PolygonShape troncoShape = new PolygonShape();
        troncoShape.setAsBox(largo*WORLD_TO_BOX,ancho*WORLD_TO_BOX,
                new Vector2(-30*WORLD_TO_BOX,50*WORLD_TO_BOX),angulo);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = troncoShape;
         //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_TRONCO;
        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA;
        fixtureDef.friction = 0f;

        troncoBody.createFixture(fixtureDef).setUserData("tronco");

    }
}
