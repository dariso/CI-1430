package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Meli.
 */
public class ManzanaScreen extends InputAdapter implements Screen {

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
    private Sprite manzanaBrilla1Sprite;//Agregar para que brille un objeto
    private Sprite manzanaBrilla2Sprite;//Agregar para que brille un objeto
    private Sprite nubeIzqSprite;//Agregar para la nube
    private Sprite nubeDerSprite;//Agregar para la nube


    private Texture manzanaBrilla1Img;  //Agregar para que brille un objeto
    private Texture manzanaBrilla2Img;  //Agregar para que brille un objeto
    private Texture nubeIzqImg;  //Agregar para la nube
    private Texture nubeDerImg;  //Agregar para la nube
    private Sprite manzanaSprite;
    private Texture gotaImage;
    private Texture manzanaImg;
    private Sprite troncoDerSprite;
    private Sprite troncoIzqSprite;
    private Texture troncoDerImage;
    private Texture troncoIzqImage;
    private Texture backgroundImage;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float vel = 10;
    private Body ground;
    private Gota enki;
    private Manzana manzana;
    private Tronco tronko;
    private Tronco tronko2;
    private DistanceJoint joint;
    boolean PAUSE = false;
    private Body body;
    private Body body2;
    int espere = 0;    //Agregar para que brille el objeto en el intervalo de tiempo

    public ManzanaScreen(final com.puddle_slide.game.Puddle_Slide elJuego) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
        manzanaImg = new Texture (Gdx.files.internal("manzana.png"));
        troncoDerImage = new Texture(Gdx.files.internal("troncoDer.png"));
        troncoIzqImage = new Texture(Gdx.files.internal("troncoIzq.png"));
        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        manzanaBrilla1Img = new Texture(Gdx.files.internal("ManzanaBrillante1.png")); //Agregar para que brille un objeto
        manzanaBrilla2Img = new Texture(Gdx.files.internal("ManzanaBrillante2.png")); //Agregar para que brille un objeto
        nubeIzqImg = new Texture(Gdx.files.internal("NubeIzq.png")); //Agregar para la nube
        nubeDerImg = new Texture(Gdx.files.internal("NubeDer.png")); //Agregar para la nube


        manzanaBrilla1Sprite = new Sprite(manzanaBrilla1Img); //Agregar para que brille un objeto
        manzanaBrilla2Sprite = new Sprite(manzanaBrilla2Img); //Agregar para que brille un objeto
        nubeIzqSprite = new Sprite(nubeIzqImg); //Agregar para la nube
        nubeDerSprite = new Sprite(nubeDerImg); //Agregar para la nube
        gotaSprite = new Sprite(gotaImage);
        manzanaSprite = new Sprite(manzanaImg);
        troncoDerSprite = new Sprite(troncoDerImage);
        troncoIzqSprite = new Sprite(troncoIzqImage);
        gotaSprite.setPosition(Gdx.graphics.getWidth()/2 *WORLD_TO_BOX , Gdx.graphics.getHeight() * WORLD_TO_BOX);
        manzanaSprite.setPosition(0,0);
        troncoDerSprite.setPosition(200*WORLD_TO_BOX, 390*WORLD_TO_BOX);
        troncoIzqSprite.setPosition(600*WORLD_TO_BOX, 200*WORLD_TO_BOX);

        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle,textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    private Vector2 vec = new Vector2();

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        if(Gdx.input.isTouched()) {
            if (joint != null) {
                world.destroyJoint(joint);
                joint = null;
            }
            //Manzana cae
        }

        //Si no esta en pausa actualiza las posiciones
        if(!PAUSE){
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1/45f, 6, 2);
        }
        this.repintar();
    }

    public void repintar(){
        gotaSprite.setPosition(enki.getX(), enki.getY());
        gotaSprite.setRotation(enki.getAngulo() * MathUtils.radiansToDegrees);

        manzanaSprite.setPosition(manzana.getX(), manzana.getY());
        manzanaSprite.setRotation(manzana.getAngulo() * MathUtils.radiansToDegrees);

        //Dibuja los sprites
        this.game.batch.begin();  //Creacion de la manzana
        this.game.batch.draw(backgroundImage, 0, 0);
        //Agregar para que brille el objeto y cambie de Sprite por rangos de tiempo, además en pausa deja de brillar
        if(0<=espere && espere<15||PAUSE) {
            this.game.batch.draw(manzanaSprite, manzanaSprite.getX(), manzanaSprite.getY(), manzana.getOrigen().x, manzana.getOrigen().y, manzanaSprite.getWidth(),
                    manzanaSprite.getHeight(), manzanaSprite.getScaleX(), manzanaSprite.getScaleY(), manzanaSprite.getRotation());
        }
        if(15<=espere && espere<30&&!PAUSE){
            this.game.batch.draw(manzanaBrilla1Sprite, manzanaSprite.getX(), manzanaSprite.getY(), manzana.getOrigen().x, manzana.getOrigen().y, manzanaBrilla1Sprite.getWidth(),
                    manzanaBrilla1Sprite.getHeight(), manzanaBrilla1Sprite.getScaleX(), manzanaBrilla1Sprite.getScaleY(), manzanaSprite.getRotation());
        }
        if(30<=espere && espere<=45&&!PAUSE){
            this.game.batch.draw(manzanaBrilla2Sprite, manzanaSprite.getX(), manzanaSprite.getY(), manzana.getOrigen().x, manzana.getOrigen().y, manzanaBrilla2Sprite.getWidth(),
                    manzanaBrilla2Sprite.getHeight(), manzanaBrilla2Sprite.getScaleX(), manzanaBrilla2Sprite.getScaleY(), manzanaSprite.getRotation());
        }
        this.game.batch.draw(gotaSprite, gotaSprite.getX(), gotaSprite.getY(), enki.getOrigen().x, enki.getOrigen().y, gotaSprite.getWidth(),
                gotaSprite.getHeight(), gotaSprite.getScaleX(), gotaSprite.getScaleY(), gotaSprite.getRotation());
        this.game.batch.draw(troncoDerSprite, troncoDerSprite.getX()-100, troncoDerSprite.getY()+310,troncoDerSprite.getX(),troncoDerSprite.getY(),troncoDerSprite.getWidth(),
                troncoDerSprite.getHeight()-20, troncoDerSprite.getScaleX(), troncoDerSprite.getScaleY(),  -0.30f*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoIzqSprite, troncoIzqSprite.getX()+250, troncoIzqSprite.getY()-70,troncoIzqSprite.getX(),troncoIzqSprite.getY(),troncoIzqSprite.getWidth(),
                troncoIzqSprite.getHeight(), troncoIzqSprite.getScaleX(), troncoIzqSprite.getScaleY(),  0.23f*MathUtils.radiansToDegrees);
        this.game.batch.end();

        //Agregar para que brille un objeto en el intervalo
        if(espere==45){
            espere=0;
        }
        espere++;

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        //  world.setContactListener(new MyContactListener());

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

        //Definicion de Bordes de Pantalla de Juego
        EdgeShape groundEdge = new EdgeShape();
        BodyDef groundDef = new BodyDef();
        FixtureDef fixtureDefIzq = new FixtureDef();
        FixtureDef fixtureDefDer = new FixtureDef();
        FixtureDef fixtureDefPiso = new FixtureDef();
        groundDef.position.set(new Vector2(0,0));
        groundDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(groundDef);

        //definicion borde Izquierdo
        groundEdge.set(-1 * WORLD_TO_BOX,-35 * WORLD_TO_BOX,-1 * WORLD_TO_BOX, camera.viewportHeight * WORLD_TO_BOX);
        fixtureDefIzq.shape = groundEdge;
        fixtureDefIzq.density = 0;
        ground.createFixture(fixtureDefIzq);
        fixtureDefIzq.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefIzq.filter.maskBits = FigureId.BIT_MANZANA|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefIzq).setUserData("borde_izq");

        //definicion Piso
        groundEdge.set(-180 * WORLD_TO_BOX, -1 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, -1 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_MANZANA|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");

        //definicion borde Derecho
        groundEdge.set((camera.viewportWidth+1) * WORLD_TO_BOX, -35*WORLD_TO_BOX, (camera.viewportWidth+1)*WORLD_TO_BOX, camera.viewportHeight*WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_MANZANA|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefDer).setUserData("borde_der");

        groundEdge.dispose();

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(0, 0);
        body = world.createBody(bd);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10*WORLD_TO_BOX, 320*WORLD_TO_BOX, new Vector2(600*WORLD_TO_BOX, 200*WORLD_TO_BOX), 1.75f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = FigureId.BIT_TRONCO;
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA;
        fixtureDef.friction = 0f;

        body.createFixture(fixtureDef).setUserData("tronco");

        bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(0, 0);
        body2 = world.createBody(bd);

        polygonShape = new PolygonShape();
        polygonShape.setAsBox(10*WORLD_TO_BOX, 250*WORLD_TO_BOX, new Vector2(200*WORLD_TO_BOX, 390*WORLD_TO_BOX),-1.80f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = FigureId.BIT_TRONCO;
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA;
        fixtureDef.friction = 0f;

        body2.createFixture(fixtureDef).setUserData("tronco");

        //Creacion de la manzana
        manzana = new Manzana(world, manzanaSprite.getX(), manzanaSprite.getY(), manzanaSprite.getWidth(), manzanaSprite.getHeight());
        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Joint
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.initialize(body, manzana.getManzanaBody(), new Vector2(250*WORLD_TO_BOX, 390*WORLD_TO_BOX) , new Vector2(manzana.getX(),manzana.getY()) );
        jointDef.collideConnected = true;
        jointDef.length = 1;
        joint = (DistanceJoint) world.createJoint(jointDef);

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
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        gotaImage.dispose();
        manzanaImg.dispose();
        backgroundImage.dispose();
    }

    public void pauseGame(){
        if(PAUSE){
            PAUSE=false;
            buttonPause.setText("Pausa");
        }else{
            PAUSE=true;
            buttonPause.setText("Atrás");
        }
    }
}
