package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class Puddle_Slide extends Game {
    SpriteBatch batch;
    BitmapFont font;
    private Vector3 touchPos;
    public MainMenuScreen menu;//Agregado

    @Override
    public void create () {
        batch = new SpriteBatch();
        touchPos = new Vector3();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
        this.setScreen(new MainStartScreen(this, menu));
    }

    @Override
    public void render () {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}