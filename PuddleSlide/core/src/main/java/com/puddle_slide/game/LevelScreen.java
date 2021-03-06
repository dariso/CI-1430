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

public class LevelScreen implements Screen {
    final Puddle_Slide game;
    OrthographicCamera camera;
    FileHandle filehandle;
    TextureAtlas textura;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonPuas;
    private TextButton buttonRama;
    private TextButton buttonSeccion1;
    private TextButton buttonTercera;
    private TextButton buttonManzana;
    private TextButton buttonReturn;
    private Label title;
    private World world;
    private Texture backgroundImage;

    public LevelScreen(final Puddle_Slide elJuego) {

        game = elJuego;
        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle, textura);
        buttonPuas = new TextButton("Puas", skin);
        buttonManzana = new TextButton("Manzana", skin);
        buttonRama = new TextButton("Ramas", skin);
        buttonSeccion1 = new TextButton("Seccion 1", skin);

        buttonTercera = new TextButton("Seccion 3", skin);

        buttonReturn = new TextButton("Regresar", skin);
        title = new Label("Niveles de prueba", skin);
        title.setFontScale(2.0f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        stage = new Stage(new StretchViewport(game.V_WIDTH, game.V_HEIGHT));
        table = new Table();
        backgroundImage = new Texture(Gdx.files.internal("fondotemp.png"));
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

    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        // world.setContactListener(new MyContactListener());
        buttonReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        buttonManzana.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ManzanaScreen(game));
            }
        });

        buttonRama.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new RamasScreen(game, world));
            }
        });
        buttonTercera.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new TerceraScreen(game));
            }
        });

        buttonTercera.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new TerceraScreen(game));
            }
        });


        buttonPuas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PuasScreen(game));
            }
        });


        buttonSeccion1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Seccion1Screen(game));
            }
        });


        table.add(title).colspan(2).center().padBottom(60).row();
        table.add(buttonManzana).colspan(2).center().size(camera.viewportWidth / 4, camera.viewportHeight / 10).padBottom(40).row();
        table.add(buttonRama).colspan(2).center().size(camera.viewportWidth / 4, camera.viewportHeight / 10).padBottom(40).row();
        table.add(buttonSeccion1).colspan(2).center().size(camera.viewportWidth / 4, camera.viewportHeight / 10).padBottom(40).row();
        table.add(buttonTercera).colspan(2).center().size(camera.viewportWidth / 4, camera.viewportHeight / 10).padBottom(40).row();
        table.add(buttonPuas).colspan(2).center().size(camera.viewportWidth / 4, camera.viewportHeight / 10).padBottom(40).row();
        table.add(buttonReturn).colspan(2).center().size(camera.viewportWidth / 4, camera.viewportHeight / 10);

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

    }
}
