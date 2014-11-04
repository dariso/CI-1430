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
    Fixture objetoA;
    Fixture objetoB;
    Fixture objetoContrario;
    Vector2 impulso = new Vector2();    // Contiene los valores del impulso aplicado a la gota cuando choca con el hongo
    Vector2 punto = new Vector2();      // Contiene el punto en el que se le va a aplicar ese impulso a la gota
    SoundControl sonido = new SoundControl();

    @Override
    public void beginContact(Contact contact) {
        objetoA = contact.getFixtureA();
        objetoB = contact.getFixtureB();

        if(objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota" ){

            if(objetoB.getUserData() == "gota"){
                objetoContrario = contact.getFixtureA();
            }else{
                objetoContrario = contact.getFixtureB();
            }

            String contrario;
            contrario = (String) objetoContrario.getUserData();
            if(contrario.equals("hoja")){
                //objetoContrario.getBody().applyAngularImpulse(500,true);
                sonido.sonidoHoja();

            }else if(contrario.equals("hojaBasica")){
            }else if(contrario.equals("hongo")){

                impulso.x = 450;
                impulso.y = 20;

                //Para saber a que lado aplicar el impulso a la gota
                if(objetoA.getBody().getPosition().x > objetoB.getBody().getPosition().x){
                    punto.x = objetoB.getBody().getPosition().x;
                    punto.y = objetoB.getBody().getPosition().y;
                }else{
                    punto.x = -objetoB.getBody().getPosition().x;
                    punto.y = -objetoB.getBody().getPosition().y;
                }
                objetoB.getBody().applyLinearImpulse(impulso.x, impulso.y, punto.x, punto.y, true);
                sonido.sonidoHoja();

            }else if(contrario.equals("manzana")){
                sonido.sonidoManzana();
            }else if(contrario.equals("troncoIzq")||contrario.equals("troncoDer")){
                sonido.sonidoTronco();
            }else if(contrario=="puas"){

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
