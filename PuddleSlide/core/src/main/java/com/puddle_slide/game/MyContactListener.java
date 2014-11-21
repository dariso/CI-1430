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
    private volatile static MyContactListener uniqueInstance;
    private boolean contactoConHoja;
    Fixture objetoA;
    Fixture objetoB;
    Fixture objetoContrario;
    Fixture objetoGota;
    Vector2 impulso = new Vector2();    // Contiene los valores del impulso aplicado a la gota cuando choca con el hongo
    Vector2 punto = new Vector2();      // Contiene el punto en el que se le va a aplicar ese impulso a la gota
    SoundControl sonido;
    boolean muerta;
    float posMuertaX;
    float posMuertaY;

    public MyContactListener(SoundControl sonido){
        this.sonido=sonido;

    }
    public static MyContactListener getInstancia(SoundControl sonido){

        if(uniqueInstance==null){
            synchronized (MyContactListener.class){
                if(uniqueInstance==null){
                    uniqueInstance = new MyContactListener(sonido);
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void beginContact(Contact contact) {
        objetoA = contact.getFixtureA();
        objetoB = contact.getFixtureB();


        if(objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota" ){

            if(objetoB.getUserData() == "gota"){
                objetoContrario = contact.getFixtureA();
                objetoGota = contact.getFixtureB();
            }else{
                objetoContrario = contact.getFixtureB();
                objetoGota = contact.getFixtureA();
            }

            String contrario;
            contrario = (String) objetoContrario.getUserData();
            if(contrario.equals("hoja")){
                //  objetoGota.getBody().applyAngularImpulse(100,true);
                sonido.sonidoGota();

            }else if(contrario.equals("hojaBasica")){
            }else if(contrario.equals("hongo")){

                impulso.x = 4 * objetoA.getBody().getMass();
                impulso.y = 2 * objetoA.getBody().getMass();

                //Para saber a que lado aplicar el impulso a la gota
                if(objetoA.getBody().getPosition().x > objetoB.getBody().getPosition().x){
                    punto.x = objetoGota.getBody().getPosition().x;
                    punto.y = objetoGota.getBody().getPosition().y;
                }else{
                    punto.x = -objetoGota.getBody().getPosition().x;
                    punto.y = -objetoGota.getBody().getPosition().y;
                }
                if(!muerta) {
                    objetoGota.getBody().applyLinearImpulse(impulso.x, impulso.y, punto.x, punto.y, true);
                }
                sonido.sonidoHongo();

            }else if(contrario.equals("manzana")){
                sonido.sonidoGota();
            }else if(contrario.equals("troncoIzq") || contrario.equals("troncoDer")){
                sonido.sonidoGota();
            }else if(contrario.equals("puas")){
                muerta = true;
                posMuertaX = objetoGota.getBody().getPosition().x;
                posMuertaY = objetoGota.getBody().getPosition().y;
                objetoGota.getBody().setAwake(false);
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
    public boolean getMuerta(){
        return this.muerta;
    }

    public void setMuerta(boolean revive){
        this.muerta = revive;
    }

    public float getMuertaX(){
        return this.posMuertaX;
    }

    public float getMuertaY(){
        return this.posMuertaY;
    }
}
