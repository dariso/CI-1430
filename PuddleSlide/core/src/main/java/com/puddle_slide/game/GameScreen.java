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
    private Texture gotaImage;
    private Texture hojaImg;
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
    private Tronco troncoTecho;
    private Tronco troncoAuxiliar;
    private Tronco troncoDinamico;
    private DistanceJoint jointHojaTroncoIzq;
    private DistanceJoint jointHojaTroncoDer;
    private MouseJoint mouseJoint;

    boolean PAUSE = false;

    public GameScreen(final com.puddle_slide.game.Puddle_Slide elJuego,MyContactListener escuchadorColision,World world) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
        hojaImg = new Texture (Gdx.files.internal("hoja2.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondoMontanas.png"));
        stage = new Stage(new StretchViewport(game.V_WIDTH,game.V_HEIGHT));
        table = new Table();

        gotaSprite = new Sprite(gotaImage);
        hojaSprite = new Sprite(hojaImg);

        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle,textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        this.escuchadorColision = escuchadorColision;
        this.world=world;
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
        this.game.batch.begin();
        //this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.draw(hojaSprite, hojaSprite.getX(), hojaSprite.getY(),hojaSprite.getOriginX(), hojaSprite.getOriginY(), hojaSprite.getWidth(),
                hojaSprite.getHeight(), hojaSprite.getScaleX(), hojaSprite.getScaleY(), hojaSprite.getRotation());
        this.game.batch.draw(gotaSprite, gotaSprite.getX(), gotaSprite.getY(), enki.getOrigen().x, enki.getOrigen().y, gotaSprite.getWidth(),
                gotaSprite.getHeight(), gotaSprite.getScaleX(), gotaSprite.getScaleY(), gotaSprite.getRotation());
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

        //Creacion de la hoja
        hoja = new HojaBasica(world, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getWidth(), hojaSprite.getHeight());

        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Creacion del tronco que sostiene la hoja
        troncoTecho = new Tronco(world, (camera.viewportWidth-875)*WORLD_TO_BOX, (camera.viewportHeight-250)*WORLD_TO_BOX, 300, 100, 0.75f, true, false);

        //Creacion del tronco que sostendrá al tronco que cuelga
        troncoAuxiliar = new Tronco(world, (camera.viewportWidth-475)*WORLD_TO_BOX, (camera.viewportHeight-250/*Estaba en 350*/)*WORLD_TO_BOX, 350, 100, 0.65f, true, false);

        //Creacion del tronco que cuelga e interactua con los demas objetos de juego
        troncoDinamico = new Tronco(world, (camera.viewportWidth-475)*WORLD_TO_BOX, (camera.viewportHeight-450/*Estaba en 550*/)*WORLD_TO_BOX, 350, 100, 0.65f, true, true);

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

        jointHojaTroncoIzq = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del segundo joint entre el tronco que estara colgando
        jointDef.localAnchorA.set(troncoAuxiliar.getTroncoBody().getLocalPoint(new Vector2(7.3279f, 7.0234f)));
        jointDef.localAnchorB.set(troncoDinamico.getTroncoBody().getLocalPoint(new Vector2(7.3279f, 5.4223f)));
        jointDef.bodyA = troncoAuxiliar.getTroncoBody();
        jointDef.bodyB = troncoDinamico.getTroncoBody();
        jointDef.length = 1.75f;

        jointHojaTroncoDer = (DistanceJoint) world.createJoint(jointDef);

        //definicion Piso
        groundEdge.set(-1 * WORLD_TO_BOX, 5 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, 5 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_HOJABASICA|FigureId.BIT_HOJA|FigureId.BIT_GOTA|FigureId.BIT_TRONCO;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");

        //definicion borde Derecho
        groundEdge.set((camera.viewportWidth+1) * WORLD_TO_BOX, -35*WORLD_TO_BOX, (camera.viewportWidth+1)*WORLD_TO_BOX, camera.viewportHeight*WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_HOJABASICA|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
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

    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {
            boolean r;
            if(!fixture.testPoint(tmp.x, tmp.y))
                return true;
            if(fixture.getBody() == hoja.getHojaBody() || fixture.getBody() == troncoDinamico.getTroncoBody()) {
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