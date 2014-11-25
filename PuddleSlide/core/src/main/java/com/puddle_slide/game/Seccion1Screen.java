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
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by xia on 11/24/14.
 */
public class Seccion1Screen extends InputAdapter implements Screen {

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
    private Sprite gotafantasmaSprite;
    private Sprite puasSprite;
    private Sprite gotaMuertaSprite;
    private Sprite hojaSprite;
    private Sprite ramaSprite;
    private Texture ramaImg;
    private Texture gotaImage;
    private Texture gotaMuertaImage;
    private Texture gotaFantasmaImage;
    private Texture puasImg;
    private Texture hojaImg;
    private Texture backgroundImage;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float vel = 10;
    private float deltaAcumulado = 0;
    private float acumuladorCamara = 0;
    private Body ground;
    private Gota enki;
    boolean PAUSE = false;
    float volar = (float) 0.01;
    MyContactListener escuchadorColision;

    //Troncos Estructurales
    private TroncoQuebradizo tronco1;
    private TroncoQuebradizo tronco2;
    private TroncoQuebradizo tronco3;
    private TroncoQuebradizo tronco4;
    private TroncoQuebradizo tronco5;
    private TroncoQuebradizo tronco6;
    private TroncoQuebradizo tronco7;
    private TroncoQuebradizo tronco8;
    private TroncoQuebradizo tronco9;
    private TroncoQuebradizo tronco10;
    private TroncoQuebradizo tronco11;
    private TroncoQuebradizo tronco12;
    private TroncoQuebradizo tronco13;
    private TroncoQuebradizo tronco14;
    private TroncoQuebradizo tronco15;
    private TroncoQuebradizo tronco16;
    private TroncoQuebradizo tronco17;
    private TroncoQuebradizo tronco18;
    private TroncoQuebradizo troncoSalida;
    private Tronco troncoGrande1;

    //Inicio (hoja y rama)
    private Hoja hoja;
    private Rama rama;

    //Objetos intensos
    private Hongo hongo1;
    private Hongo hongo2;
    private Hongo hongo3;
    private Hongo hongo4;
    private Hongo hongo5;

    private Puas pua1;
    private Puas pua2;
    private Puas pua3;
    private Puas pua4;
    private Puas pua5;
    private Puas pua6;
    private Puas pua7;
    private Puas pua8;
    private Puas pua9;
    private Puas pua10;
    private Puas pua11;
    private Puas pua12;

    //Objetos auxiliares
    private TroncoQuebradizo troncoSuspendido1;
    private TroncoQuebradizo troncoSuspendido2;
    private TroncoQuebradizo troncoSuspendido3;

    private Manzana manzana1;
    private Manzana manzana2;
    private Manzana manzana3;

    //Joints
    private MouseJoint dragJoint;
    private MouseJoint resorteJoint;
    private DistanceJoint troncoTecho1_joint1;
    private DistanceJoint troncoTecho1_joint2;
    private DistanceJoint troncoTecho2_joint1;
    private DistanceJoint troncoTecho2_joint2;
    private DistanceJoint troncoTecho3_joint1;
    private DistanceJoint troncoTecho3_joint2;


    public Seccion1Screen(final com.puddle_slide.game.Puddle_Slide elJuego) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty1.png"));
        puasImg = new Texture (Gdx.files.internal("puasP.png"));
        gotaFantasmaImage =  new Texture (Gdx.files.internal("fantasmita.png"));
        gotaMuertaImage = new Texture(Gdx.files.internal("gotaM.png"));
        hojaImg = new Texture(Gdx.files.internal("hoja2.png"));
        ramaImg = new Texture(Gdx.files.internal("ramaIzquierdaAbajo.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondoMontanas.png"));

        gotaSprite = new Sprite(gotaImage);
        gotaMuertaSprite = new Sprite(gotaMuertaImage);
        gotafantasmaSprite = new Sprite(gotaFantasmaImage);
        puasSprite = new Sprite(puasImg);
        hojaSprite = new Sprite(hojaImg);
        ramaSprite = new Sprite(ramaImg);

        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle,textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        escuchadorColision = MyContactListener.getInstancia(SoundControl.getInstancia());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        //Si no esta en pausa actualiza las posiciones
        if (!PAUSE) {
            camera.update();
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1 / 60f, 6, 2);

        }
        this.actualizarSprites();

    }

    private void moveCamera(float y) {
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 - y, 0);
    }

    public void actualizarSprites() {
        gotaSprite.setPosition(enki.getX(), enki.getY());
        gotaSprite.setRotation(enki.getAngulo() * MathUtils.radiansToDegrees);

        gotaMuertaSprite.setPosition(enki.getX(), enki.getY());
        gotaMuertaSprite.setRotation(enki.getAngulo() * MathUtils.radiansToDegrees);


        //Dibuja los sprites
        this.game.batch.begin();
        this.game.batch.draw(backgroundImage, 0, -camera.viewportHeight*2);
        if(!escuchadorColision.getMuerta()) {
            this.game.batch.draw(gotaSprite, gotaSprite.getX(), gotaSprite.getY(), enki.getOrigen().x, enki.getOrigen().y, gotaSprite.getWidth(),
                    gotaSprite.getHeight(), gotaSprite.getScaleX(), gotaSprite.getScaleY(), gotaSprite.getRotation());
        }else{
            this.game.batch.draw(gotaMuertaSprite, gotaSprite.getX(), gotaSprite.getY(), enki.getOrigen().x, enki.getOrigen().y, gotaMuertaSprite.getWidth(),
                    gotaMuertaSprite.getHeight(), gotaMuertaSprite.getScaleX(), gotaMuertaSprite.getScaleY(), gotaSprite.getRotation());
            this.game.batch.draw(gotafantasmaSprite, enki.getX()-64, enki.getY() + volar);
            volar++;
        }
        this.game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    public void paintSprite(Sprite sprite, ObjetoJuego objeto){
        
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this.escuchadorColision);
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




        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Creacion de la hoja
        hoja = new Hoja(world, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getWidth(), hojaSprite.getHeight());

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
        fixtureDefIzq.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefIzq).setUserData("borde_izq");

        //definicion Piso
        groundEdge.set(-180 * WORLD_TO_BOX, -1 * WORLD_TO_BOX, -camera.viewportWidth * 2 * WORLD_TO_BOX, -1 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");

        //definicion borde Derecho
        groundEdge.set((camera.viewportWidth+1) * WORLD_TO_BOX, -35*WORLD_TO_BOX, (camera.viewportWidth+1)*WORLD_TO_BOX, camera.viewportHeight*WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefDer).setUserData("borde_der");

        groundEdge.dispose();

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
        puasImg.dispose();
        backgroundImage.dispose();
        gotaMuertaImage.dispose();
        gotaFantasmaImage.dispose();
        ramaImg.dispose();
        hojaImg.dispose();
        escuchadorColision.setMuerta(false);
    }

    public void pauseGame(){
        if(PAUSE){
            PAUSE = false;
            buttonPause.setText("Pausa");
        }else{
            PAUSE = true;
            buttonPause.setText("Atrás");
        }
    }
}
