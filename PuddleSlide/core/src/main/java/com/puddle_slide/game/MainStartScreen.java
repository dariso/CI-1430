package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainStartScreen implements Screen {
    final MainMenuScreen menuScreen;
    final Puddle_Slide game;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private Label titulo;
    private Label titulo2;

    public MainStartScreen(final Puddle_Slide gam, final MainMenuScreen menu) {
        menuScreen = menu;
        game = gam;
        filehandle= Gdx.files.internal("skins/menuSkin.json");
        textura=new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin= new Skin(filehandle,textura);
        titulo = new Label("Puddle Slide",skin);
        titulo2 = new Label("Presione la Pantalla",skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            game.setScreen(new com.puddle_slide.game.GameScreen(game));
            game.setScreen(new GameScreen(game));
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {
        table.add(titulo).colspan(2).center().padBottom(40).row();
        titulo2.setFontScale((float) 0.7);
        table.add(titulo2).colspan(2).center().padBottom(40).row();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }
}


