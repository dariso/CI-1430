package com.puddle_slide.game;

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


public class OptionsScreen implements Screen {
    final Puddle_Slide game;
    OrthographicCamera camera;
    FileHandle filehandle;
    TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private TextButton buttonSonido;
    private TextButton buttonVideo;
    private TextButton buttonReturn;
    private Label title;

    public OptionsScreen(final Puddle_Slide elJuego) {

        game = elJuego;
        filehandle= Gdx.files.internal("skins/menuSkin.json");
        textura=new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin= new Skin(filehandle,textura);
        buttonSonido = new TextButton("Sonido", skin);
        buttonVideo = new TextButton("Video", skin);
        buttonReturn = new TextButton("Regresar",skin);
        title = new Label("Opciones",skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,game.V_WIDTH,game.V_HEIGHT);
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

        buttonReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        buttonSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ComingSoonScreen(new OptionsScreen(game)));
            }
        });

        buttonVideo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ComingSoonScreen(new OptionsScreen(game)));
            }
        });



        table.add(title).colspan(2).center().padBottom(40).row();
        table.add(buttonVideo).size(150,60).padBottom(20).row();
        table.add(buttonSonido).size(150,60).padBottom(20).row();
        table.add(buttonReturn).size(150,60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }



    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}