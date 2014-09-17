package com.monchie.puddleslide;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class PSGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture gotaImage;
    private Texture backgroundImage;
    private OrthographicCamera camera;
    private Vector3 touchPos;

	
	@Override
	public void create () {
		batch = new SpriteBatch();

		gotaImage = new Texture("gotty.png");
        backgroundImage = new Texture("background.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        batch.draw(backgroundImage,0,0);
		batch.draw(gotaImage,(800/2-64/2),(480/2-64/2));
		batch.end();
	}
}
