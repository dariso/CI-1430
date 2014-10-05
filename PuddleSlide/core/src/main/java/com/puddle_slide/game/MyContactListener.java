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
       // Vector2 velocidad = new Vector2(0, 8);
        Vector2 velocidad=new Vector2((float)Math.cos(30*Math.PI/180),(float)Math.sin(30*Math.PI/180));
              // System.out.println(objetoA.getUserData()+", "+objetoB.getUserData());
           if( (objetoA.getUserData()=="hoja"||objetoB.getUserData()=="hoja")&& (objetoA.getUserData()=="gota"||objetoB.getUserData()=="gota")){
               //objetoA es la gota


                   objetoB.getBody().applyForceToCenter(300,0,true);
                   //System.out.println("HOLA");


                  // objetoB.getBody().setLinearVelocity(velocidad);
                   System.out.println("HOLA");


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
