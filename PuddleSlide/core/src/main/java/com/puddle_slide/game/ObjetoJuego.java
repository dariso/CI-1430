package com.puddle_slide.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by xia on 11/25/14.
 */
public interface ObjetoJuego {
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
    public float getAngulo();
    public Vector2 getOrigen();
    public float getMasa();

}
