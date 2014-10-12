package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by kalam on 11/10/2014.
 * SoundControl es el encargado de reproducir todos los sonidos del juego
 */
public class SoundControl {
    Sound hojaSound;

    public SoundControl(){

        hojaSound = Gdx.audio.newSound(Gdx.files.internal("boinki.wav"));

    }

    public void sonidoHoja(){

        hojaSound.play();
        
    }
}
