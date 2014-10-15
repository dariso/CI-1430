package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by kalam on 11/10/2014.
 * SoundControl es el encargado de reproducir todos los sonidos del juego
 */
public class SoundControl {
   private Sound hojaSound;
   private Sound troncoSound;

    public SoundControl(){

        hojaSound = Gdx.audio.newSound(Gdx.files.internal("boinki.wav"));
        troncoSound = Gdx.audio.newSound(Gdx.files.internal("branch.mp3"));
    }

    public void sonidoHoja(){
        hojaSound.play();
    }
    public void sonidoTronco(){ troncoSound.play();}
}
