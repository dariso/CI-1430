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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class GameScreen extends InputAdapter implements Screen {

    final Puddle_Slide game;
    private OrthographicCamera camera;
    private FileHandle filehandle;
    private TextureAtlas textura;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonPause;
    private TextButton buttonRegresar;
    private Sprite gotaSprite;
    private Sprite hojaSprite;
    private Sprite troncoTechoSprite;
    private Sprite troncoIzqSprite;
    private Sprite troncoDinamicoSprite;
    private Sprite hongoSprite;
    private Sprite manzanaSprite;
    private Sprite puas1Sprite;
    private Sprite puas2Sprite;
    private Texture gotaImage;
    private Texture hojaImg;
    private Texture troncoDerImg;
    private Texture troncoIzqImg;
    private Texture hongoImg;
    private Texture manzanaImg;
    private Texture puasImg;
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
    private Tronco troncoTecho;
    private Tronco troncoAuxiliar;
    private Tronco troncoDinamico;

    //troncos estructurales
    private Tronco tronco1;
    private Tronco tronco2;
    private Tronco tronco3;
    private Tronco tronco4;

    private DistanceJoint jointHojaTroncoIzq;
    private DistanceJoint jointHojaTroncoDer;
    private DistanceJoint jointTroncoTroncoIzq;
    private DistanceJoint jointTroncoTroncoDer;
    private DistanceJoint jointPuasTronco;
    private DistanceJoint jointManzanaTronco;
    private MouseJoint mouseJoint;

    boolean PAUSE = false;

    public GameScreen(final com.puddle_slide.game.Puddle_Slide elJuego,MyContactListener escuchadorColision,World world) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
        hojaImg = new Texture (Gdx.files.internal("hoja2.png"));
        troncoDerImg = new Texture(Gdx.files.internal("troncoDer.png"));
        troncoIzqImg = new Texture(Gdx.files.internal("troncoIzq.png"));
        puasImg = new Texture(Gdx.files.internal("Puas3.png"));
        hongoImg = new Texture(Gdx.files.internal("hongosNaranja2.png"));
        manzanaImg = new Texture(Gdx.files.internal("manzana.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondoMontanas.png"));
        stage = new Stage(new StretchViewport(game.V_WIDTH,game.V_HEIGHT));
        table = new Table();

        gotaSprite = new Sprite(gotaImage);
        hojaSprite = new Sprite(hojaImg);
        troncoTechoSprite = new Sprite(troncoDerImg);
        troncoDinamicoSprite = new Sprite(troncoDerImg);
        troncoIzqSprite = new Sprite(troncoIzqImg);
        puas1Sprite = new Sprite(puasImg);
        puas2Sprite = new Sprite(puasImg);
        hongoSprite = new Sprite(hongoImg);
        manzanaSprite = new Sprite(manzanaImg);

        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle,textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        this.escuchadorColision = escuchadorColision;
        this.world = world;
        camera.setToOrtho(false,game.V_WIDTH,game.V_HEIGHT);

    }

    private Vector2 vec = new Vector2();

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0,0,1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        //Si no esta en pausa actualiza las posiciones
        if(!PAUSE){
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1/45f, 6, 2);
            hoja.mover(vec);
            // En vec se va a actualizar la posicion del cuerpo de la hoja
        }
        this.repintar();

    }

    public void repintar(){
        gotaSprite.setPosition(enki.getX(), enki.getY());
        gotaSprite.setRotation(enki.getAngulo() * MathUtils.radiansToDegrees);

        hojaSprite.setPosition(hoja.getX(), hoja.getY());
        hojaSprite.setOrigin(hoja.getOrigen().x, hoja.getOrigen().y);
        hojaSprite.setRotation(hoja.getAngulo() * MathUtils.radiansToDegrees);

        troncoDinamicoSprite.setPosition(troncoDinamico.getX(), troncoDinamico.getY());
        troncoDinamicoSprite.setOrigin(troncoDinamico.getOrigen().x, troncoDinamico.getOrigen().y);
        troncoDinamicoSprite.setRotation(troncoDinamico.getAngulo());

        puas1Sprite.setPosition(puas1.getX(), puas1.getY());
        puas1Sprite.setOrigin(puas1.getOrigen().x, puas1.getOrigen().y);
        puas1Sprite.setRotation(puas1.getAngulo() * MathUtils.radiansToDegrees);

        puas2Sprite.setPosition(puas2.getX(), puas2.getY());
        puas2Sprite.setOrigin(puas2.getOrigen().x, puas2.getOrigen().y);
        puas2Sprite.setRotation(puas2.getAngulo() * MathUtils.radiansToDegrees);

        hongoSprite.setPosition(hongo.getX(), hongo.getY());
        hongoSprite.setOrigin(hongo.getOrigen().x, hongo.getOrigen().y);
        hongoSprite.setRotation(hongo.getAngulo() * MathUtils.radiansToDegrees);

        manzanaSprite.setPosition(manzana.getX(), manzana.getY());
        manzanaSprite.setOrigin(manzana.getOrigen().x, manzana.getOrigen().y);
        manzanaSprite.setRotation(manzana.getAngulo() * MathUtils.radiansToDegrees);


        this.game.batch.begin();
       // this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.draw(hojaSprite, hojaSprite.getX(), hojaSprite.getY(),hojaSprite.getOriginX(), hojaSprite.getOriginY(), hojaSprite.getWidth(),
                hojaSprite.getHeight(), hojaSprite.getScaleX(), hojaSprite.getScaleY(), hojaSprite.getRotation());
        this.game.batch.draw(gotaSprite, gotaSprite.getX(), gotaSprite.getY(), enki.getOrigen().x, enki.getOrigen().y, gotaSprite.getWidth(),
                gotaSprite.getHeight(), gotaSprite.getScaleX(), gotaSprite.getScaleY(), gotaSprite.getRotation());
        this.game.batch.draw(troncoTechoSprite, troncoTecho.getX(), troncoTecho.getY()+10, troncoTecho.getOrigen().x, troncoTecho.getOrigen().y, troncoTechoSprite.getWidth()/2,
                troncoTechoSprite.getHeight()/2, troncoTechoSprite.getScaleX(), troncoTechoSprite.getScaleY(), troncoTecho.getAngulo()*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoTechoSprite, troncoAuxiliar.getX()-75, troncoAuxiliar.getY(), troncoAuxiliar.getOrigen().x, troncoAuxiliar.getOrigen().y, troncoTechoSprite.getWidth()-100,
                troncoTechoSprite.getHeight()/2, troncoTechoSprite.getScaleX(), troncoTechoSprite.getScaleY(), (troncoAuxiliar.getAngulo()-0.2f)*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoDinamicoSprite, troncoDinamico.getX()-67, troncoDinamico.getY()+45, troncoDinamico.getOrigen().x, troncoDinamico.getOrigen().y, troncoDinamico.getWidth()+10,
                troncoTechoSprite.getHeight()/2, troncoDinamicoSprite.getScaleX(), troncoDinamicoSprite.getScaleY(), (troncoDinamico.getAngulo()-0.15f)*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoIzqSprite, tronco1.getX(), tronco1.getY()+10, tronco1.getOrigen().x, tronco1.getOrigen().y, troncoIzqSprite.getWidth()/2,
                troncoIzqSprite.getHeight()/2, troncoIzqSprite.getScaleX(), troncoIzqSprite.getScaleY(), tronco1.getAngulo()*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoIzqSprite, tronco2.getX(), tronco2.getY()+10, tronco2.getOrigen().x, tronco2.getOrigen().y, troncoIzqSprite.getWidth()/2,
                troncoIzqSprite.getHeight()/2, troncoIzqSprite.getScaleX(), troncoIzqSprite.getScaleY(), tronco2.getAngulo()*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoIzqSprite, tronco3.getX(), tronco3.getY()-50, tronco3.getOrigen().x, tronco3.getOrigen().y, troncoIzqSprite.getWidth()/2,
                troncoIzqSprite.getHeight()/2, troncoIzqSprite.getScaleX(), troncoIzqSprite.getScaleY(), tronco3.getAngulo()*MathUtils.radiansToDegrees);
        this.game.batch.draw(troncoIzqSprite, tronco4.getX(), tronco4.getY()+20, tronco4.getOrigen().x, tronco4.getOrigen().y, (troncoIzqSprite.getWidth()/2) + 50,
                troncoIzqSprite.getHeight()/2, troncoIzqSprite.getScaleX(), troncoIzqSprite.getScaleY(), tronco4.getAngulo()*MathUtils.radiansToDegrees);
        this.game.batch.draw(puas1Sprite, puas1Sprite.getX(), puas1Sprite.getY(), puas1.getOrigen().x, puas1.getOrigen().y, 150,
                150, puas1Sprite.getScaleX(), puas1Sprite.getScaleY(), puas1Sprite.getRotation());
        this.game.batch.draw(puas2Sprite, puas2Sprite.getX(), puas2Sprite.getY(), puas2.getOrigen().x, puas2.getOrigen().y, puas2Sprite.getWidth()-100,
                puas2Sprite.getHeight()-100, puas2Sprite.getScaleX(), puas2Sprite.getScaleY(), puas2Sprite.getRotation());
        this.game.batch.draw(hongoSprite, hongoSprite.getX(), hongoSprite.getY(), hongo.getOrigen().x, hongo.getOrigen().y, 259,
                250, hongoSprite.getScaleX(), hongoSprite.getScaleY(), hongoSprite.getRotation());
        this.game.batch.draw(manzanaSprite, manzanaSprite.getX(), manzanaSprite.getY(), manzana.getOrigen().x, manzana.getOrigen().y, manzanaSprite.getWidth(),
                manzanaSprite.getHeight(), manzanaSprite.getScaleX(), manzanaSprite.getScaleY(), manzanaSprite.getRotation());
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

        gotaSprite.setPosition((camera.viewportWidth-875)*WORLD_TO_BOX, (camera.viewportHeight-150)*WORLD_TO_BOX);
        hojaSprite.setPosition(0, (camera.viewportHeight-350)*WORLD_TO_BOX);
        troncoTechoSprite.setPosition((camera.viewportWidth-875)*WORLD_TO_BOX, (camera.viewportHeight-250)*WORLD_TO_BOX);
        puas1Sprite.setPosition((camera.viewportWidth-25)*WORLD_TO_BOX, (camera.viewportHeight-225)*WORLD_TO_BOX);
        hongoSprite.setPosition((camera.viewportWidth-250)*WORLD_TO_BOX, 0);
        puas2Sprite.setPosition((camera.viewportWidth-950)*WORLD_TO_BOX, 0);
        manzanaSprite.setPosition((camera.viewportWidth-850)*WORLD_TO_BOX, (camera.viewportHeight-550)*WORLD_TO_BOX);

        //Creacion de la hoja
        hoja = new HojaBasica(world, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getWidth(), hojaSprite.getHeight());

        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Creacion del hongo
        hongo = new Hongo(world, hongoSprite.getX(), hongoSprite.getY(),259,250);

        //Creacion de la manzana
        manzana = new Manzana(world, manzanaSprite.getX(), manzanaSprite.getY(), manzanaSprite.getWidth(), manzanaSprite.getHeight());

        //Creacion de las puas que cuelgan del tronco
        puas1 = new Puas(world, puas1Sprite.getX(), puas1Sprite.getY(), 150, 150, true);

        //Creacion de las puas que se encuentran en el piso
        puas2 = new Puas(world, puas2Sprite.getX(), puas2Sprite.getY(), puas2Sprite.getWidth()-100, puas2Sprite.getHeight()-100, false);

        //Creacion del tronco que sostiene la hoja
        troncoTecho = new Tronco(world, troncoTechoSprite.getX(), troncoTechoSprite.getY(), 300, 100, 0.75f, true, false);

        //Creacion del tronco que sostendrá al tronco que cuelga
        troncoAuxiliar = new Tronco(world, (camera.viewportWidth-475)*WORLD_TO_BOX, (camera.viewportHeight-250)*WORLD_TO_BOX, 350, 100, 0.65f, true, false);

        //Creacion del tronco que cuelga e interactua con los demas objetos de juego
        troncoDinamico = new Tronco(world, (camera.viewportWidth-475)*WORLD_TO_BOX, (camera.viewportHeight-450)*WORLD_TO_BOX, 350, 100, 0.65f, true, true);

        //Creacion de los troncos que formaran una estructura
        tronco1 = new Tronco(world, (camera.viewportWidth-325)*WORLD_TO_BOX, (camera.viewportHeight-250)*WORLD_TO_BOX, 275, 100, -0.35f, false, false);
        tronco2 = new Tronco(world, (camera.viewportWidth-525)*WORLD_TO_BOX, (camera.viewportHeight-450)*WORLD_TO_BOX, 275, 100, -0.55f, false, false);
        tronco3 = new Tronco(world, (camera.viewportWidth-425)*WORLD_TO_BOX, (camera.viewportHeight-350)*WORLD_TO_BOX, 300, 100, -2.00f, false, false);
        tronco4 = new Tronco(world, (camera.viewportWidth-1100)*WORLD_TO_BOX, (camera.viewportHeight-450)*WORLD_TO_BOX, 300, 100, -0.55f, false, false);

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
        fixtureDefIzq.filter.maskBits = FigureId.BIT_HOJABASICA|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefIzq).setUserData("borde_izq");

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

        jointHojaTroncoDer = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del primer joint entre el tronco que estara colgando
        jointDef.localAnchorA.set(troncoAuxiliar.getTroncoBody().getLocalPoint(new Vector2(4.144f, 7.06875f)));
        jointDef.localAnchorB.set(troncoDinamico.getTroncoBody().getLocalPoint(new Vector2(4.176f, 5.25625f)));
        jointDef.bodyA = troncoAuxiliar.getTroncoBody();
        jointDef.bodyB = troncoDinamico.getTroncoBody();
        jointDef.length = 1.75f;

        jointTroncoTroncoIzq = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del segundo joint entre el tronco que estara colgando
        jointDef.localAnchorA.set(troncoAuxiliar.getTroncoBody().getLocalPoint(new Vector2(7.3279f, 7.0234f)));
        jointDef.localAnchorB.set(troncoDinamico.getTroncoBody().getLocalPoint(new Vector2(7.3279f, 5.4223f)));
        jointDef.bodyA = troncoAuxiliar.getTroncoBody();
        jointDef.bodyB = troncoDinamico.getTroncoBody();
        jointDef.length = 1.75f;

        jointTroncoTroncoDer = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del joint de las puas que cuelgan del tronco
        jointDef.localAnchorA.set(tronco1.getTroncoBody().getLocalPoint(new Vector2(9.312f, 5.7546f)));
        jointDef.localAnchorB.set(puas1.getPuasBody().getLocalPoint(new Vector2(9.28f, 5.3619f)));
        jointDef.bodyA = tronco1.getTroncoBody();
        jointDef.bodyB = puas1.getPuasBody();
        jointDef.length = 0.05f;

        jointPuasTronco = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del joint de la manzana que cuelga del tronco
        jointDef.localAnchorA.set(tronco4.getTroncoBody().getLocalPoint(new Vector2(2.5279f, 3.3078f)));
        jointDef.localAnchorB.set(manzana.getManzanaBody().getLocalPoint(new Vector2(2.496f, 3.1114f)));
        jointDef.bodyA = tronco4.getTroncoBody();
        jointDef.bodyB = manzana.getManzanaBody();
        jointDef.length = 0.3f;

        jointManzanaTronco = (DistanceJoint) world.createJoint(jointDef);

        //definicion Piso
        groundEdge.set(-1 * WORLD_TO_BOX, 5 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, 5 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_HOJABASICA|FigureId.BIT_PUAS|FigureId.BIT_GOTA|FigureId.BIT_TRONCO|FigureId.BIT_HONGO|FigureId.BIT_MANZANA;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");

        //definicion borde Derecho
        groundEdge.set((camera.viewportWidth+1) * WORLD_TO_BOX, -35*WORLD_TO_BOX, (camera.viewportWidth+1)*WORLD_TO_BOX, camera.viewportHeight*WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_HOJABASICA|FigureId.BIT_HOJA|FigureId.BIT_GOTA|FigureId.BIT_HONGO;
        ground.createFixture(fixtureDefDer).setUserData("borde_der");

        groundEdge.dispose();

        table.add(buttonPause).size(camera.viewportWidth/6,camera.viewportHeight/9).padTop(-600).padLeft(stage.getCamera().viewportWidth-250).row();
        table.add(buttonRegresar).size(camera.viewportWidth / 6, camera.viewportHeight / 9).padTop(-575).padBottom(-200).padLeft(stage.getCamera().viewportWidth - 250);
        table.setFillParent(true);
        stage.addActor(table);

    }

    //Para el arrastre de objetos de juego
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    int i = 0;

    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {

            if(!fixture.testPoint(tmp.x, tmp.y))
                return true;
            if(jointTroncoTroncoDer == null && jointTroncoTroncoIzq != null && fixture.getBody() == troncoDinamico.getTroncoBody()){
                world.destroyJoint(jointTroncoTroncoIzq);
                jointTroncoTroncoIzq = null;
            }
            if(fixture.getBody() == troncoDinamico.getTroncoBody() && jointTroncoTroncoDer != null){
                world.destroyJoint(jointTroncoTroncoDer);
                jointTroncoTroncoDer = null;
            }
            if(fixture.getBody() == manzana.getManzanaBody() && jointManzanaTronco != null) {
                world.destroyJoint(jointManzanaTronco);
                jointManzanaTronco = null;
            }

            if(fixture.getBody() == hoja.getHojaBody()) {
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
        if(mouseJoint == null)
            return false;
        camera.unproject(tmp.set(screenX, screenY, 0));
        tmp.x *= WORLD_TO_BOX;
        tmp.y *= WORLD_TO_BOX;
        mouseJoint.setTarget(tmp2.set(tmp.x, tmp.y));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        i++;
        if(mouseJoint == null)
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
        backgroundImage.dispose();
        world.dispose();
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