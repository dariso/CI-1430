package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by kalam on 11/10/2014.
 * SoundControl es el encargado de reproducir todos los sonidos del juego
 */
public class SoundControl {
    private volatile static SoundControl uniqueInstance;
   private Sound hojaSound;
   private Sound troncoSound;
   private Sound manzanaSound;

    public SoundControl(){

        hojaSound = Gdx.audio.newSound(Gdx.files.internal("boinki.mp3"));
        troncoSound = Gdx.audio.newSound(Gdx.files.internal("branch.mp3"));
        manzanaSound = Gdx.audio.newSound(Gdx.files.internal("CaeManzana.mp3"));

    }
    public static SoundControl getInstancia(){

        if(uniqueInstance==null){
            synchronized (MyContactListener.class){
                if(uniqueInstance==null){
                    uniqueInstance = new SoundControl();
                }
            }
        }
        return uniqueInstance;
    }

    public void sonidoHoja(){
        hojaSound.play();
    }
    public void sonidoTronco(){ troncoSound.play();}

    public void sonidoManzana(){ manzanaSound.play();}

}
