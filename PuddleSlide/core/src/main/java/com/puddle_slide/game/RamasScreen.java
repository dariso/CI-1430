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


public class RamasScreen extends InputAdapter implements Screen {

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
    private Sprite ramaSprite;
    private Texture gotaImage;
    private Texture hojaImg;
    private Texture ramaImg;
    private Texture backgroundImage;
    MyContactListener escuchadorColision;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body ground;
    private Gota enki;
    private HojaBasica hoja;
    private Rama rama;

    private DistanceJoint hojaRamaJoint;
    private MouseJoint mouseJoint;
    private MouseJoint pruebaResorteJoint;

    boolean PAUSE = false;

    public RamasScreen(final com.puddle_slide.game.Puddle_Slide elJuego, World world) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty1.png"));
        hojaImg = new Texture(Gdx.files.internal("hoja2.png"));
        ramaImg = new Texture(Gdx.files.internal("RamaIzquierdaParaHojas.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondoMontanas.png"));
        stage = new Stage(new StretchViewport(game.V_WIDTH, game.V_HEIGHT));
        table = new Table();

        gotaSprite = new Sprite(gotaImage);
        hojaSprite = new Sprite(hojaImg);
        ramaSprite = new Sprite(ramaImg);

        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle, textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        SoundControl sonido = SoundControl.getInstancia();
        this.world = world;
        escuchadorColision = MyContactListener.getInstancia(sonido);
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);

    }


    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        //Si no esta en pausa actualiza las posiciones
        if (!PAUSE) {
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1 / 45f, 6, 2);
           // moveCamera(enki.getX(), enki.getY());
            //camera.update();
            // En vec se va a actualizar la posicion del cuerpo de la hoja
        }
        this.repintar();

    }

    public void repintar() {
        gotaSprite.setPosition(enki.getX(), enki.getY());
        gotaSprite.setRotation(enki.getAngulo() * MathUtils.radiansToDegrees);

        hojaSprite.setPosition(hoja.getX(), hoja.getY());
        hojaSprite.setOrigin(hoja.getOrigen().x, hoja.getOrigen().y);
        hojaSprite.setRotation(hoja.getAngulo() * MathUtils.radiansToDegrees);

        ramaSprite.setPosition(rama.getX(), rama.getY());

        this.game.batch.begin();
        // this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.draw(hojaSprite, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getOriginX(), hojaSprite.getOriginY(), hojaSprite.getWidth(),
                hojaSprite.getHeight(), hojaSprite.getScaleX(), hojaSprite.getScaleY(), hojaSprite.getRotation());
        this.game.batch.draw(ramaSprite, ramaSprite.getX(), ramaSprite.getY());

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

        gotaSprite.setPosition((camera.viewportWidth - 825) * WORLD_TO_BOX, (camera.viewportHeight - 300) * WORLD_TO_BOX);
        hojaSprite.setPosition((camera.viewportWidth - 900) * WORLD_TO_BOX, (camera.viewportHeight - 400) * WORLD_TO_BOX);
        ramaSprite.setPosition(0, (camera.viewportHeight - 300) * WORLD_TO_BOX);

        //Creacion de la hoja
        hoja = new HojaBasica(world, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getWidth(), hojaSprite.getHeight());

        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Creacion de la rama para hojas
        rama = new Rama(world, ramaSprite.getX(), ramaSprite.getY(), ramaSprite.getWidth(), ramaSprite.getHeight(), 1);

        //Definicion del joint entre la hoja y la rama
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(rama.getRamaBody().getLocalPoint(new Vector2(1.3920f, 5.2f)));
        jointDef.localAnchorB.set(hoja.getHojaBody().getLocalPoint(new Vector2(1.4079f, 4.736f)));
        jointDef.bodyA = rama.getRamaBody();
        jointDef.bodyB = hoja.getHojaBody();
        jointDef.length = 0.3f;

        hojaRamaJoint = (DistanceJoint) world.createJoint(jointDef);

        //Prueba MouseJoint como resorte

        MouseJointDef md = new MouseJointDef();
        md.bodyA = rama.getRamaBody();
        md.bodyB = hoja.getHojaBody();
        md.maxForce = 500 * hoja.getHojaBody().getMass();
        md.target.set(hoja.getX()*WORLD_TO_BOX, hoja.getY()*WORLD_TO_BOX);
        pruebaResorteJoint = (MouseJoint) world.createJoint(md);

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
        groundEdge.set(-1 * WORLD_TO_BOX, -35 * WORLD_TO_BOX, -1 * WORLD_TO_BOX, camera.viewportHeight * WORLD_TO_BOX);
        fixtureDefIzq.shape = groundEdge;
        fixtureDefIzq.density = 0;
        ground.createFixture(fixtureDefIzq);
        fixtureDefIzq.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefIzq.filter.maskBits = FigureId.BIT_HOJABASICA | FigureId.BIT_HOJA | FigureId.BIT_GOTA;
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
        groundEdge.set((camera.viewportWidth + 1) * WORLD_TO_BOX, -35 * WORLD_TO_BOX, (camera.viewportWidth + 1) * WORLD_TO_BOX, camera.viewportHeight * WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_HOJABASICA | FigureId.BIT_HOJA | FigureId.BIT_GOTA | FigureId.BIT_HONGO;
        ground.createFixture(fixtureDefDer).setUserData("borde_der");

        groundEdge.dispose();

        table.add(buttonPause).size(camera.viewportWidth / 6, camera.viewportHeight / 12).padTop(-650).padLeft(stage.getCamera().viewportWidth - 250).row();
        table.add(buttonRegresar).size(camera.viewportWidth / 6, camera.viewportHeight / 12).padTop(-675).padBottom(-200).padLeft(stage.getCamera().viewportWidth - 250);
        table.setFillParent(true);
        stage.addActor(table);

    }

    public void moveCamera(float x, float y) {
        camera.position.set(camera.viewportWidth / 2, y / 3 + 250, 0);
    }


    //Para el arrastre de objetos de juego
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();

    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {

            if (fixture.getBody() == hoja.getHojaBody()) {
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


