package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by kalam on 05/10/2014.
 */
public class LevelScreen implements Screen {
    final Puddle_Slide game;
    OrthographicCamera camera;
    FileHandle filehandle;
    TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private TextButton buttonHoja;
    private TextButton buttonHongo;
    private TextButton buttonTronco;
    private TextButton buttonManzana;
    private TextButton buttonReturn;
    private Label title;
    MyContactListener escuchadorColision;
    private World world;

    public LevelScreen(final Puddle_Slide elJuego){

        game = elJuego;
        filehandle= Gdx.files.internal("skins/menuSkin.json");
        textura=new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin= new Skin(filehandle,textura);
        buttonHoja = new TextButton("Hoja", skin);
        buttonHongo = new TextButton("Hongo", skin);
        buttonTronco = new TextButton("Tronco", skin);
        buttonManzana = new TextButton("Manzana", skin);
        buttonReturn = new TextButton("Regresar",skin);
        title = new Label("Niveles de prueba",skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        escuchadorColision=new MyContactListener();

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

    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(new MyContactListener());
        buttonReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        buttonHoja.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new HojaScreen(game,escuchadorColision,world));
            }
        });

        buttonHongo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new HongoScreen(game,escuchadorColision,world));
            }
        });
        buttonTronco.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new TroncoScreen(game));
            }
        });
        buttonManzana.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ManzanaScreen(game));
            }
        });




        table.add(title).colspan(2).center().padBottom(40).row();
        table.add(buttonHoja).size(150,60).padBottom(20).spaceRight(50.0f);
        table.add(buttonHongo).size(150,60).padBottom(20).row();
        table.add(buttonTronco).size(150,60).padBottom(20).spaceRight(50.0f);
        table.add(buttonManzana).size(150,60).padBottom(20).row();
        table.add(buttonReturn).colspan(2).center().size(150,60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

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

    }
}
