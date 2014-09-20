package com.monchie.puddleslide;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MainMenuScreen implements Screen {

final PSGame game;
OrthographicCamera camera;
FileHandle filehandle;
TextureAtlas textura;
private Skin skin;
private Stage stage = new Stage();
private Table table = new Table();
private TextButton buttonPlay;
private TextButton buttonExit;
private TextButton buttonOpciones;
private Label title;
    
    public MainMenuScreen(final PSGame juego) {
    	
	    game =juego;
	    filehandle= Gdx.files.internal("skins/menuSkin.json");
	    textura=new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
	    skin= new Skin(filehandle,textura);
	    buttonPlay = new TextButton("Jugar", skin);
	    buttonExit = new TextButton("Salir", skin);
        buttonOpciones = new TextButton("Opciones",skin);
	    title = new Label("Puddle Slide",skin);
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    
    }
    
    @Override
    public void render(float delta) {
    	
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        
    }
    
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void show() {
		
		 buttonPlay.addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y) {
		            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
		        }
		    });
	    buttonExit.addListener(new ClickListener(){
	    		@Override
	        	public void clicked(InputEvent event, float x, float y) {
	            Gdx.app.exit();
	        }
	    });
		
		
	    table.add(title).padBottom(40).row();
	    table.add(buttonPlay).size(150,60).padBottom(20).row();
	    table.add(buttonExit).size(150,60).padBottom(20).row();
        table.add(buttonOpciones).size(150,60).padBottom(20).row();
	    table.setFillParent(true);
	    stage.addActor(table);
	    Gdx.input.setInputProcessor(stage);
	    
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		 stage.dispose();
	     skin.dispose();
		
	}

}
