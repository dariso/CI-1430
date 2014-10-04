package com.puddle_slide.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by kalam on 03/10/2014.
 */


public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture objetoA = contact.getFixtureA();
        Fixture objetoB = contact.getFixtureB();
        System.out.println(objetoA.getUserData()+", "+objetoB.getUserData());

    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Solto");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
