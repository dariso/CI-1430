package com.monchie.puddleslide;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	final PSGame game;
	OrthographicCamera camera;
	Texture gotaImage;
	Texture backgroundImage;
	   public GameScreen(final PSGame juego) {
	        this.game = juego;
	        
	        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
	        backgroundImage = new Texture(Gdx.files.internal("background.png"));
	        

	        camera = new OrthographicCamera();
	        camera.setToOrtho(false, 800, 480);

	
	    }

	

	    @Override
	    public void render(float delta) {
	       
	    	Gdx.gl.glClearColor(0, 2, 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			 camera.update();
			 game.batch.setProjectionMatrix(camera.combined);
			
			game.batch.begin();
			game.batch.draw(backgroundImage,0,0);
			game.batch.draw(gotaImage,(800/2-64/2),(480/2-64/2));
			game.batch.end();
				
		

	 

	  
	    }

	    @Override
	    public void resize(int width, int height) {
	    }

	    @Override
	    public void show() {
	        //musica de fondo

	    }

	    @Override
	    public void hide() {
	    }

	    @Override
	    public void pause() {
	    }

	    @Override
	    public void resume() {
	    }

	    @Override
	    public void dispose() {
	    	gotaImage.dispose();
	    	backgroundImage.dispose();
	      
	    }
}

