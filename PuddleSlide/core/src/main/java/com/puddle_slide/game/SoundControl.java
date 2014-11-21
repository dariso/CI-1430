package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by kalam on 11/10/2014.
 * SoundControl es el encargado de reproducir todos los sonidos del juego
 */
public class SoundControl {
    private volatile static SoundControl uniqueInstance;
    private Sound gotaSound;
    private Sound troncoSound;
    private Sound manzanaSound;
    private Sound hongoSound;

    private SoundControl(){

        gotaSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        troncoSound = Gdx.audio.newSound(Gdx.files.internal("branch.mp3"));
        manzanaSound = Gdx.audio.newSound(Gdx.files.internal("CaeManzana.mp3"));
        hongoSound = Gdx.audio.newSound(Gdx.files.internal("boinki.mp3"));

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

    public void sonidoGota(){
        gotaSound.play();
    }
    public void sonidoTronco(){ troncoSound.play();}
    public void sonidoHongo(){
        hongoSound.play();
    }
    public void sonidoManzana(){ manzanaSound.play();}

}
