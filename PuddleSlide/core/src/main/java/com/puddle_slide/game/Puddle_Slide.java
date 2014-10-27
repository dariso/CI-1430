package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Puddle_Slide extends Game {
    SpriteBatch batch;
    BitmapFont font;
    float V_WIDTH;
    float V_HEIGHT;



    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        V_WIDTH = (float)Gdx.graphics.getWidth();
        V_HEIGHT = (float)Gdx.graphics.getHeight();
        this.setScreen(new MainStartScreen(this));
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
