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


        if( (objetoA.getUserData() == "hoja" ||objetoA.getUserData() == "hojader" || objetoB.getUserData() == "hoja" || objetoB.getUserData() == "hojader") && (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            //objetoA es la gota
            if(objetoA.getUserData() == "gota"){
                objetoA.getBody().applyAngularImpulse(100,true);
            }else{
                objetoB.getBody().applyAngularImpulse(100,true);
               // System.out.println(objetoB.getUserData());
             //   System.out.println(objetoA.getUserData());
               // sonido.sonidoHoja();
            }
        }else if((objetoA.getUserData() == "hojaBasica" || objetoB.getUserData() == "hojaBasica") && (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            //objetoA es la gota
            if(objetoA.getUserData() == "gota"){

            }else{

               // System.out.println(objetoB.getUserData());
               // System.out.println(objetoA.getUserData());
            }

        }/*else if((objetoA.getUserData() == "manzana" || objetoB.getUserData() == "manzana") && (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            //objetoA es la gota
            if(objetoA.getUserData() == "gota"){

            }else{
                sonido.sonidoManzana();
            }

        }

        Vector2 impulso = new Vector2();    // Contiene los valores del impulso aplicado a la gota cuando choca con el hongo
        Vector2 punto = new Vector2();      // Contiene el punto en el que se le va a aplicar ese impulso a la gota
        if( (objetoA.getUserData() == "hongo" || objetoB.getUserData() == "hongo") && (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            //objetoA es la gota
            if(objetoA.getUserData() == "gota"){
                impulso.x = 450;
                impulso.y = 20;
                //Para saber a que lado aplicar el impulso a la gota
                if(objetoA.getBody().getPosition().x > objetoB.getBody().getPosition().x){
                    punto.x = objetoA.getBody().getPosition().x;
                }else{
                    punto.x = -objetoA.getBody().getPosition().x;
                }
                objetoA.getBody().applyLinearImpulse(impulso.x, impulso.y, punto.x, punto.y, true);
            }else{
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
            }
        }

        else if((objetoA.getUserData() == "troncoDer" || objetoB.getUserData() == "troncoDer") &&
                (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
                    sonido.sonidoTronco();

        }

        else if((objetoA.getUserData() == "troncoIzq" || objetoB.getUserData() == "troncoIzq") &&
                (objetoA.getUserData() == "gota" || objetoB.getUserData() == "gota")){
            sonido.sonidoTronco();
        }*/
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
