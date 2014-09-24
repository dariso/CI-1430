package com.puddle_slide.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class GameScreen implements Screen {

    final Puddle_Slide game;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage = new Stage();
    private Table table = new Table();
    private TextButton buttonPause;
    private TextButton buttonRegresar;
    private Sprite gotaSprite;
    private Sprite hojaSprite;
    private Texture gotaImage;
    private Texture hojaImg;
    private Texture backgroundImage;
    private Texture backgroundPause;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private BodyDef hojaDef;
    private Body hojaBody;
    private BodyDef gotaDef;
    private Body gotaBody;
    boolean PAUSE = false;

    public GameScreen(final Puddle_Slide elJuego) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
        hojaImg = new Texture (Gdx.files.internal("bucket.png"));
        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        gotaSprite = new Sprite(gotaImage);
        hojaSprite = new Sprite(hojaImg);
        gotaSprite.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);
        hojaSprite.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4, 0);
        filehandle= Gdx.files.internal("skins/menuSkin.json");
        textura=new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin= new Skin(filehandle,textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0,1f,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        //Si no esta en pausa actualiza las posiciones
        if(PAUSE == false){
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1 / 60f, 6, 2);
        }
        this.repintar();

    }

    public void repintar(){
        gotaSprite.setPosition(gotaBody.getPosition().x, gotaBody.getPosition().y);
        this.game.batch.begin();
        this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.draw(hojaSprite, hojaSprite.getX(), hojaSprite.getY());
        this.game.batch.draw(gotaSprite, gotaSprite.getX(), gotaSprite.getY());
        this.game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -98), true);
        debugRenderer = new Box2DDebugRenderer();

        //Boton de Pausa

        buttonPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGame();
            }
        });
        buttonRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });
        // Definicion de la hoja
        hojaDef = new BodyDef();
        hojaDef.position.set(hojaSprite.getX(), hojaSprite.getY());
        hojaBody = world.createBody(hojaDef);
        PolygonShape hojaShape = new PolygonShape();
        hojaShape.setAsBox(hojaSprite.getWidth(), hojaSprite.getY());
        hojaBody.createFixture(hojaShape, 0f);
        hojaShape.dispose();

        //Definicion de la gota

        gotaDef = new BodyDef();
        gotaDef.type = BodyDef.BodyType.DynamicBody;
        gotaDef.position.set(gotaSprite.getX(), gotaSprite.getY());
        gotaBody = world.createBody(gotaDef);
        CircleShape gotaShape = new CircleShape();
        gotaShape.setRadius(gotaSprite.getWidth()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = gotaShape;
        fixtureDef.density = 999.97f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.4f;
        gotaBody.createFixture(fixtureDef);
        gotaShape.dispose();
        table.add(buttonPause).size(140,40).padTop(-160).padLeft(450).row();
        table.add(buttonRegresar).size(140,40).padTop(-30).padBottom(250).padLeft(450);
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    pauseGame();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        gotaImage.dispose();
        backgroundImage.dispose();
    }
    public void pauseGame(){
        if(PAUSE == true){
            PAUSE=false;
            buttonPause.setText("Pausa");
        }else{
            PAUSE=true;
            buttonPause.setText("Atrás");
        }
     }

    }


