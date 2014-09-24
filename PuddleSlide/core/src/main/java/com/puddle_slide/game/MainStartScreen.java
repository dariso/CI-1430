package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

public class MainStartScreen implements Screen {
    final com.puddle_slide.game.MainMenuScreen menuScreen;
    final com.puddle_slide.game.Puddle_Slide game;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private Label titulo;
    private Label titulo2;
    private TextureRegion textureRegion;
    private Texture imagent;
    private Image imagen;

    public MainStartScreen(final com.puddle_slide.game.Puddle_Slide gam, final com.puddle_slide.game.MainMenuScreen menu) {
        menuScreen = menu;
        game = gam;
        filehandle= Gdx.files.internal("skins/menuSkin.json");
        textura=new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin= new Skin(filehandle,textura);
        titulo = new Label("Puddle Slide",skin);
        titulo2 = new Label("Presionar Pantalla",skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        imagent = new Texture(Gdx.files.internal("gotitaYagua_1.png"));
        imagen = new Image(imagent);
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
            game.setScreen(new com.puddle_slide.game.GameScreen(game));
            ((Game)Gdx.app.getApplicationListener()).setScreen(new com.puddle_slide.game.MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {
        imagen.setScaling(Scaling.fit);
        table.add(imagen).colspan(2).center().padBottom(-40).row();
        table.add(titulo).colspan(2).center().padBottom(20).row();
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


