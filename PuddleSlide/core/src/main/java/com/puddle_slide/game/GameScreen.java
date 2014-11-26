package com.puddle_slide.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class GameScreen extends InputAdapter implements Screen {

    final Puddle_Slide game;

    private float acumuladorCamara = 0;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonPause;
    private TextButton buttonRegresar;
    private Label textoFPS;
    private Sprite gotaSprite;
    private Sprite gotaMuertaSprite;
    private Sprite gotafantasmaSprite;
    private Sprite hojaSprite;
    private Sprite troncoTechoSprite;
    private Sprite troncoDerSprite;
    private Sprite troncoIzqSprite;
    private final Sprite troncoQuebraSprite;
    private Sprite troncoDinamicoSprite;
    private Sprite hongoSprite;
    private Sprite manzanaSprite;
    private Sprite lianaSprite;
    private Sprite puas1Sprite;
    private Sprite puas2Sprite;
    private Texture gotaImage;
    private Texture hojaImg;
    private Texture troncoDerImg;
    private Texture troncoIzqImg;
    private Texture troncoQuebraImg;
    private Texture hongoImg;
    private Texture manzanaImg;
    private Texture puasImg;
    private Texture gotaMuertaImage;
    private Texture gotaFantasmaImage;
    private Texture backgroundImage;
    MyContactListener escuchadorColision;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float vel = 10;
    private Body ground;
    private Gota enki;
    private HojaBasica hoja;
    private Hongo hongo;
    private Puas puas1;
    private Puas puas2;
    private Manzana manzana;


    //troncos estructurales
    private Tronco tronco1;
    private Tronco tronco2;
    private Tronco tronco3;
    private Tronco tronco4;
    private Tronco tronco5;

    private TroncoQuebradizo troncoQ1;
    private TroncoQuebradizo troncoQ2;
    private TroncoQuebradizo troncoQ3;
    private TroncoQuebradizo troncoQ4;
    private TroncoQuebradizo troncoQ5;
    private TroncoQuebradizo troncoQ6;
    private TroncoQuebradizo troncoQ7;


    private DistanceJoint jointManzanaTronco;
    private DistanceJoint troncoQ1HojaJoint;
    private MouseJoint resorteJoint;
    private MouseJoint mouseJoint;
    private DistanceJoint troncoQ4PuasJoint;
    private DistanceJoint troncoQ4Puas2Joint;
    private DistanceJoint troncoQ3ManzanaJoint;

    boolean PAUSE = false;
    float volar = (float) 0.01;


    public GameScreen(final com.puddle_slide.game.Puddle_Slide elJuego, MyContactListener escuchadorColision, World world) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty1.png"));
        gotaFantasmaImage = new Texture(Gdx.files.internal("fantasmita.png"));
        gotaMuertaImage = new Texture(Gdx.files.internal("gotaM.png"));
        hojaImg = new Texture(Gdx.files.internal("hoja2.png"));
        troncoDerImg = new Texture(Gdx.files.internal("troncoDer.png"));
        troncoIzqImg = new Texture(Gdx.files.internal("troncoIzq.png"));
        troncoQuebraImg = new Texture(Gdx.files.internal("troncoQuebradizo.png"));
        puasImg = new Texture(Gdx.files.internal("puasP.png"));
        hongoImg = new Texture(Gdx.files.internal("hongosNaranja2.png"));
        manzanaImg = new Texture(Gdx.files.internal("manzana.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondoConCharco.png"));
        stage = new Stage(new StretchViewport(game.V_WIDTH, game.V_HEIGHT));
        table = new Table();

        gotaSprite = new Sprite(gotaImage);
        gotaMuertaSprite = new Sprite(gotaMuertaImage);
        gotafantasmaSprite = new Sprite(gotaFantasmaImage);
        hojaSprite = new Sprite(hojaImg);
        troncoTechoSprite = new Sprite(troncoDerImg);
        troncoDinamicoSprite = new Sprite(troncoDerImg);
        troncoIzqSprite = new Sprite(troncoIzqImg);
        troncoDerSprite = new Sprite(troncoDerImg);
        troncoQuebraSprite = new Sprite(troncoQuebraImg);
        puas1Sprite = new Sprite(puasImg);
        puas2Sprite = new Sprite(puasImg);
        hongoSprite = new Sprite(hongoImg);
        manzanaSprite = new Sprite(manzanaImg);


        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle, textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        textoFPS = new Label(" ", skin);
        textoFPS.setFontScale(1.2f);
        camera = new OrthographicCamera();
        this.escuchadorColision = escuchadorColision;
        this.world = world;
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 + game.V_HEIGHT, 0);
    }

    private Vector2 vec = new Vector2();

    @Override
    public void render(float delta) {

        camera.update();
        Gdx.gl.glClearColor(0, 0, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        //Si no esta en pausa actualiza las posiciones
        if (!PAUSE) {
            /*if gota esta en checkpoint, bajar camara
            * else renderizar normalmente
            */
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1 / 45f, 6, 2);
            camera.update();
        }
        this.renderizarSprites();
    }

    public void renderizarSprites() {

        this.game.batch.begin();
        //this.game.batch.draw(backgroundImage, 0, 0);

        this.paintSprite(troncoDerSprite, tronco1);
        this.paintSprite(troncoIzqSprite, tronco2);
        this.paintSprite(troncoDerSprite, tronco3);
        this.paintSprite(troncoQuebraSprite, troncoQ1);
        this.paintSprite(troncoQuebraSprite, troncoQ2);
        this.paintSprite(troncoQuebraSprite, troncoQ3);
        this.paintSprite(troncoQuebraSprite, troncoQ4);
        this.paintSprite(troncoQuebraSprite, troncoQ5);
        this.paintSprite(troncoQuebraSprite, troncoQ6);
        this.paintSprite(troncoQuebraSprite, troncoQ7);
        this.paintSprite(puas1Sprite, puas1);
        this.paintSprite(puas1Sprite, puas2);
        this.paintSprite(manzanaSprite, manzana);
        this.paintSprite(hojaSprite, hoja);

        if (!escuchadorColision.getMuerta()) {
            this.paintSprite(gotaSprite, enki);
        } else {
            this.game.batch.draw(gotaMuertaSprite, gotaSprite.getX(), gotaSprite.getY(), enki.getOrigen().x, enki.getOrigen().y, gotaMuertaSprite.getWidth(),
                    gotaMuertaSprite.getHeight(), gotaMuertaSprite.getScaleX(), gotaMuertaSprite.getScaleY(), gotaSprite.getRotation());
            this.game.batch.draw(gotafantasmaSprite, gotaSprite.getX() - 64, gotaSprite.getY() + volar);
            volar++;
        }

        this.game.batch.end();

        textoFPS.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
        stage.act();
        stage.draw();
    }

    public void paintSprite(Sprite sprite, ObjetoJuego objeto) {
        sprite.setPosition(objeto.getX(), objeto.getY());
        this.game.batch.draw(sprite, objeto.getX(), objeto.getY(), objeto.getOrigen().x, objeto.getOrigen().y,
                objeto.getWidth(), objeto.getHeight(), sprite.getScaleX(), sprite.getScaleY(),
                objeto.getAngulo() * MathUtils.radiansToDegrees);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(this.escuchadorColision);
        //manejo de multiples input processors
        //primero se llama al procesador que responde a los objetos del juego
        //si este retorna falso, el input lo debe manejar el del UI ya que se toco un boton
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        //Boton de Pausa

        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGame();
            }
        });
        buttonRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        //Creacion de la gota
        enki = new Gota(world, (camera.viewportWidth - 975) * WORLD_TO_BOX, (camera.viewportHeight - 50 + game.V_HEIGHT) * WORLD_TO_BOX,
                gotaSprite.getWidth());
        //troncos seccion izquierda
        tronco1 = new Tronco(world, (camera.viewportWidth - 1150) * WORLD_TO_BOX,
                (camera.viewportHeight - 95 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoDerSprite.getWidth() / 2, troncoDerSprite.getHeight() / 2,
                -0.008f, true, false);
        tronco2 = new Tronco(world, (camera.viewportWidth - 730) * WORLD_TO_BOX,
                (camera.viewportHeight - 90 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoDerSprite.getWidth() / 2, troncoDerSprite.getHeight() / 2,
                1.60f, false, false);

        tronco3 = new Tronco(world, (camera.viewportWidth - 500) * WORLD_TO_BOX,
                (camera.viewportHeight - 640 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoDerSprite.getWidth() / 2, troncoDerSprite.getHeight() / 2,
                -0.310f, true, false);

        tronco4 = new Tronco(world, (camera.viewportWidth - 1150) * WORLD_TO_BOX,
                (camera.viewportHeight - 320) * WORLD_TO_BOX,
                troncoDerSprite.getWidth() / 2, troncoDerSprite.getHeight() / 2,
                0f, true, false);

        tronco5 = new Tronco(world, (camera.viewportWidth - 800) * WORLD_TO_BOX,
                (camera.viewportHeight - 400) * WORLD_TO_BOX,
                troncoDerSprite.getWidth() / 2, troncoDerSprite.getHeight() / 2,
                0.027f, true, false);


        troncoQ1 = new TroncoQuebradizo(world, (camera.viewportWidth - 800) * WORLD_TO_BOX,
                (camera.viewportHeight - 455 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight() / 2,
                0.050f, false);

        troncoQ2 = new TroncoQuebradizo(world, (camera.viewportWidth - 690) * WORLD_TO_BOX,
                (camera.viewportHeight - 340 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight(),
                0.026f, false);

        troncoQ3 = new TroncoQuebradizo(world, (camera.viewportWidth - 480) * WORLD_TO_BOX,
                (camera.viewportHeight - 370 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight(),
                -0.008f, false);


        //tronco que une con puas1
        troncoQ4 = new TroncoQuebradizo(world, (camera.viewportWidth - 500) * WORLD_TO_BOX,
                (camera.viewportHeight - 60 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight(),
                0, false);

        troncoQ5 = new TroncoQuebradizo(world, (camera.viewportWidth - 700) * WORLD_TO_BOX,
                (camera.viewportHeight - 60 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight(),
                0, false);

        troncoQ6 = new TroncoQuebradizo(world, (camera.viewportWidth - 350) * WORLD_TO_BOX,
                (camera.viewportHeight - 720 + game.V_HEIGHT) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight(),
                0.004f, false);

        //seccion 2

        troncoQ7 = new TroncoQuebradizo(world, (camera.viewportWidth - 730) * WORLD_TO_BOX,
                (camera.viewportHeight - 90) * WORLD_TO_BOX,
                troncoQuebraSprite.getWidth(), troncoQuebraSprite.getHeight(),
                0.004f, false);

        manzana = new Manzana(world, (camera.viewportWidth - 470) * WORLD_TO_BOX,
                (camera.viewportHeight - 520 + game.V_HEIGHT) * WORLD_TO_BOX,
                manzanaSprite.getWidth(), manzanaSprite.getHeight());

        puas1 = new Puas(world, (camera.viewportWidth - 240) * WORLD_TO_BOX,
                (camera.viewportHeight - 70 + game.V_HEIGHT) * WORLD_TO_BOX,
                puas1Sprite.getWidth() / 2, puas1Sprite.getHeight() / 2, true);

        puas2 = new Puas(world, (camera.viewportWidth - 430) * WORLD_TO_BOX,
                (camera.viewportHeight - 70 + game.V_HEIGHT) * WORLD_TO_BOX,
                puas1Sprite.getWidth() / 2, puas1Sprite.getHeight() / 2, true);


        hoja = new HojaBasica(world, (camera.viewportWidth - 820) * WORLD_TO_BOX,
                (camera.viewportHeight - 600 + game.V_HEIGHT) * WORLD_TO_BOX, hojaSprite.getWidth(),
                hojaSprite.getHeight());


        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(troncoQ1.getTroncoBody().getLocalPoint(new Vector2(2.03f, 10.75f)));
        jointDef.localAnchorB.set(hoja.getHojaBody().getLocalPoint(new Vector2(2.36f, 10.63f)));
        jointDef.bodyA = troncoQ1.getTroncoBody();
        jointDef.bodyB = hoja.getHojaBody();
        jointDef.length = 0.3f;

        troncoQ1HojaJoint = (DistanceJoint) world.createJoint(jointDef);

        jointDef.localAnchorA.set(troncoQ4.getTroncoBody().getLocalPoint(new Vector2(7.02f, 14.90f)));
        jointDef.localAnchorB.set(puas1.getPuasBody().getLocalPoint(new Vector2(7.02f, 14.90f)));
        jointDef.bodyA = troncoQ4.getTroncoBody();
        jointDef.bodyB = puas1.getPuasBody();
        jointDef.length = 0.3f;

        troncoQ4PuasJoint = (DistanceJoint) world.createJoint(jointDef);

        jointDef.localAnchorA.set(troncoQ4.getTroncoBody().getLocalPoint(new Vector2(5.12f, 14.90f)));
        jointDef.localAnchorB.set(puas2.getPuasBody().getLocalPoint(new Vector2(5.12f, 14.90f)));
        jointDef.bodyA = troncoQ4.getTroncoBody();
        jointDef.bodyB = puas2.getPuasBody();
        jointDef.length = 0.3f;

        troncoQ4Puas2Joint = (DistanceJoint) world.createJoint(jointDef);

        jointDef.localAnchorA.set(troncoQ3.getTroncoBody().getLocalPoint(new Vector2(6.35f, 11.45f)));
        jointDef.localAnchorB.set(manzana.getManzanaBody().getLocalPoint(new Vector2(6.35f, 11.45f)));
        jointDef.bodyA = troncoQ3.getTroncoBody();
        jointDef.bodyB = manzana.getManzanaBody();
        jointDef.length = 0.3f;

        troncoQ3ManzanaJoint = (DistanceJoint) world.createJoint(jointDef);


        MouseJointDef md = new MouseJointDef();
        md.bodyA = troncoQ1.getTroncoBody();
        md.bodyB = hoja.getHojaBody();
        md.maxForce = 500 * hoja.getHojaBody().getMass();
        md.target.set(hoja.getX() * WORLD_TO_BOX, hoja.getY() * WORLD_TO_BOX);
        resorteJoint = (MouseJoint) world.createJoint(md);

        /**
        //Definicion de primer Joint entre el tronco y la hoja
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(new Vector2(0.25f, 2f));
        jointDef.localAnchorB.set(new Vector2(0f, 1.3f));
        jointDef.bodyA = troncoTecho.getTroncoBody();
        jointDef.bodyB = hoja.getHojaBody();
        jointDef.collideConnected = true;
        jointDef.length = 1f;
        jointHojaTroncoIzq = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del segundo Joint entre la hoja y el tronco
        jointDef.localAnchorA.y = 1;
        jointDef.localAnchorA.x = 2;
        jointDef.localAnchorB.x = 2.5f;
        jointDef.localAnchorB.y = 1;
        jointDef.bodyA = troncoTecho.getTroncoBody();
        jointDef.bodyB = hoja.getHojaBody();
        jointDef.length = 1.75f;

        //Definicion del joint de la manzana que cuelga del tronco
        jointDef.localAnchorA.set(tronco4.getTroncoBody().getLocalPoint(new Vector2(2.5279f, 3.3078f)));
        jointDef.localAnchorB.set(manzana.getManzanaBody().getLocalPoint(new Vector2(2.496f, 3.1114f)));
        jointDef.bodyA = tronco4.getTroncoBody();
        jointDef.bodyB = manzana.getManzanaBody();
        jointDef.length = 0.3f;

        jointManzanaTronco = (DistanceJoint) world.createJoint(jointDef);
         **/

        //Definicion de Bordes de Pantalla de Juego
        EdgeShape groundEdge = new EdgeShape();
        BodyDef groundDef = new BodyDef();
        FixtureDef fixtureDefIzq = new FixtureDef();
        FixtureDef fixtureDefDer = new FixtureDef();
        FixtureDef fixtureDefPiso = new FixtureDef();
        groundDef.position.set(new Vector2(0, 0));
        groundDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(groundDef);

        //definicion borde Izquierdo
        groundEdge.set(-1 * WORLD_TO_BOX, -35 * WORLD_TO_BOX, -1 * WORLD_TO_BOX, camera.viewportHeight * 3 * WORLD_TO_BOX);
        fixtureDefIzq.shape = groundEdge;
        fixtureDefIzq.density = 0;
        ground.createFixture(fixtureDefIzq);
        fixtureDefIzq.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefIzq.filter.maskBits = FigureId.BIT_HOJABASICA | FigureId.BIT_HOJA | FigureId.BIT_GOTA | FigureId.BIT_MANZANA;
        ground.createFixture(fixtureDefIzq).setUserData("borde_izq");

        //definicion Piso
        groundEdge.set(-1 * WORLD_TO_BOX, 5 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, 5 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_HOJABASICA | FigureId.BIT_PUAS | FigureId.BIT_GOTA | FigureId.BIT_TRONCO | FigureId.BIT_HONGO | FigureId.BIT_MANZANA;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");

        //definicion borde Derecho
        groundEdge.set((camera.viewportWidth + 1) * WORLD_TO_BOX, -35 * WORLD_TO_BOX, (camera.viewportWidth + 1) * WORLD_TO_BOX, camera.viewportHeight * 3 * WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_HOJABASICA | FigureId.BIT_HOJA | FigureId.BIT_GOTA | FigureId.BIT_HONGO | FigureId.BIT_MANZANA;
        ground.createFixture(fixtureDefDer).setUserData("borde_der");

        groundEdge.dispose();

        table.add(buttonPause).size(camera.viewportWidth / 6, camera.viewportHeight / 12).padTop(-650).padLeft(stage.getCamera().viewportWidth - 250).row();
        table.add(buttonRegresar).size(camera.viewportWidth / 6, camera.viewportHeight / 12).padTop(-675).padBottom(-200).padLeft(stage.getCamera().viewportWidth - 250).row();
        table.add(textoFPS).size(camera.viewportWidth / 6, camera.viewportHeight / 12).padTop(-675).padBottom(-300).padLeft(stage.getCamera().viewportWidth - 125);
        table.setFillParent(true);
        stage.addActor(table);
    }

    public boolean bajarSiguienteSeccion() {
        acumuladorCamara += 3;
        if (acumuladorCamara > 768) {
            acumuladorCamara = 768;
        }
        moveCamera(camera.viewportWidth / 2, acumuladorCamara);
        if (acumuladorCamara == 768) {
            acumuladorCamara = 0;
            return true;
        } else {
            return false;
        }
    }

    public void moveCamera(float x, float y) {
        camera.position.set(x, y, 0);
    }

    //Para el arrastre de objetos de juego
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();

    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {

            if (!fixture.testPoint(tmp.x, tmp.y))
                return true;
          /*  else if (jointTroncoTroncoDer == null && jointTroncoTroncoIzq != null && fixture.getBody() == troncoDinamico.getTroncoBody()) {
                world.destroyJoint(jointTroncoTroncoIzq);
                jointTroncoTroncoIzq = null;
            }
            else if (fixture.getBody() == troncoDinamico.getTroncoBody() && jointTroncoTroncoDer != null) {
                world.destroyJoint(jointTroncoTroncoDer);
                jointTroncoTroncoDer = null;
            }*/
            else if (fixture.getBody() == manzana.getManzanaBody() && troncoQ3ManzanaJoint != null) {
                world.destroyJoint(troncoQ3ManzanaJoint);
                jointManzanaTronco = null;
            } else if (fixture.getBody() == hoja.getHojaBody()) {
                MouseJointDef md = new MouseJointDef();
                md.bodyA = ground;
                md.bodyB = fixture.getBody();
                md.collideConnected = true;
                md.maxForce = 1000 * fixture.getBody().getMass();
                md.target.set(tmp.x, tmp.y);
                mouseJoint = (MouseJoint) world.createJoint(md);
                fixture.getBody().setAwake(true);
            }
            return false;
        }
    };

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("X = " + tmp.x + " Y = " + tmp.y);
        camera.unproject(tmp.set(screenX, screenY, 0));
        tmp.x *= WORLD_TO_BOX;
        tmp.y *= WORLD_TO_BOX;
        world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (mouseJoint == null)
            return false;
        camera.unproject(tmp.set(screenX, screenY, 0));
        tmp.x *= WORLD_TO_BOX;
        tmp.y *= WORLD_TO_BOX;
        mouseJoint.setTarget(tmp2.set(tmp.x, tmp.y));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (mouseJoint == null)
            return false;

        world.destroyJoint(mouseJoint);
        mouseJoint = null;
        return true;
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
        hojaImg.dispose();
        troncoDerImg.dispose();
        troncoIzqImg.dispose();
        hongoImg.dispose();
        manzanaImg.dispose();
        puasImg.dispose();
        gotaMuertaImage.dispose();
        gotaFantasmaImage.dispose();
        backgroundImage.dispose();
        world.dispose();
        escuchadorColision.setMuerta(false);
    }

    public void pauseGame() {
        if (PAUSE) {
            PAUSE = false;
            buttonPause.setText("Pausa");
        } else {
            PAUSE = true;
            buttonPause.setText("Atrás");
        }
    }
}