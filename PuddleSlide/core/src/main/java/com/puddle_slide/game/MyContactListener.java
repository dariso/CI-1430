package com.puddle_slide.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by kalam on 03/10/2014.
 */


public class MyContactListener implements ContactListener {
    private boolean contactoConHoja;

    @Override
    public void beginContact(Contact contact) {

        Fixture objetoA = contact.getFixtureA();
        Fixture objetoB = contact.getFixtureB();
        SoundControl sonido = new SoundControl();
        //Vector con direccion a futuro
        Vector2 velocidad = new Vector2((float)Math.cos(30*Math.PI/180),(float)Math.sin(30*Math.PI/180));


        if( (objetoA.getUserData() == "hoja" || objetoB.getUserData() == "hoja") && (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            //objetoA es la gota
            if(objetoA.getUserData() == "gota"){
                objetoA.getBody().applyAngularImpulse(100,true);
            }else{
                objetoB.getBody().applyAngularImpulse(100,true);
                System.out.println(objetoB.getUserData());
                System.out.println(objetoA.getUserData());
                sonido.sonidoHoja();
            }
        }else if((objetoA.getUserData() == "hojaBasica" || objetoB.getUserData() == "hojaBasica") && (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            //objetoA es la gota
            if(objetoA.getUserData() == "gota"){

            }else{

                System.out.println(objetoB.getUserData());
                System.out.println(objetoA.getUserData());
            }

        }
    }

    public boolean contactoHoja(){
        return contactoConHoja;
    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
