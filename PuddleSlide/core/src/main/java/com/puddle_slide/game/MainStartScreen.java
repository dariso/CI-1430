package com.puddle_slide.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainStartScreen implements Screen {
    final com.puddle_slide.game.Puddle_Slide game;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private Label titulo;
    private Label titulo2;
    private Texture imagent;
    private Texture backgroundImage;
    private Image imagen;


    public MainStartScreen(final com.puddle_slide.game.Puddle_Slide gam) {
        game = gam;
        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle, textura);
        titulo = new Label("Puddle Slide", skin);
        titulo2 = new Label("Presionar Pantalla", skin);
        stage = new Stage(new StretchViewport(game.V_WIDTH, game.V_HEIGHT));
        table = new Table();
        titulo.setFontScale(2.0f);
        titulo2.setFontScale(1.4f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        imagent = new Texture(Gdx.files.internal("gotitaYagua_1.png"));
        imagen = new Image(imagent);
        backgroundImage = new Texture(Gdx.files.internal("fondotemp.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.begin();
        this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.end();
        stage.act();
        stage.draw();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void show() {
        imagen.setScaling(Scaling.fit);
        table.add(imagen).colspan(2).center().padBottom(-40).row();
        table.add(titulo).colspan(2).center().padBottom(20).row();
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
        this.dispose();
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


