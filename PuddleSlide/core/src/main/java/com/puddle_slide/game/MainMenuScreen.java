package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen {

    final Puddle_Slide game;
    private final Texture backgroundImage;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private TextButton buttonPlay;
    private TextButton buttonNiveles;
    private TextButton buttonOpciones;
    private Label title;
    private World world;
    MyContactListener escuchadorColision;
    SoundControl sonido;

    public MainMenuScreen(final Puddle_Slide elJuego) {

        this.game = elJuego;
        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle, textura);
        buttonPlay = new TextButton("Jugar", skin);
        buttonNiveles = new TextButton("Escoger Nivel", skin);
        buttonOpciones = new TextButton("Opciones", skin);
        title = new Label("Puddle Slide", skin);
        title.setFontScale(2.0f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        new Stage(new StretchViewport(game.V_WIDTH, game.V_HEIGHT));
        backgroundImage = new Texture(Gdx.files.internal("fondotemp.png"));
        table = new Table();
        world = new World(new Vector2(0, -9.8f), true);
        sonido = SoundControl.getInstancia();
        escuchadorColision = MyContactListener.getInstancia(sonido);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        this.game.batch.begin();
        this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, escuchadorColision, world));
            }
        });

        buttonOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new OptionsScreen(game));
            }
        });

        buttonNiveles.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen(game));
            }
        });

        table.add(title).colspan(2).center().padBottom(80).row();
        table.add(buttonPlay).colspan(2).center().size(stage.getCamera().viewportWidth / 2 - 100, stage.getCamera().viewportHeight / 4).padBottom(60).row();
        table.add(buttonNiveles).size(stage.getCamera().viewportWidth / 3 + 50, stage.getCamera().viewportHeight / 6).padBottom(20).spaceRight(50.0f);
        table.add(buttonOpciones).size(stage.getCamera().viewportWidth / 3 + 50, stage.getCamera().viewportHeight / 6).padBottom(20).row();

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
