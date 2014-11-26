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
    private Sprite gotaMuertaSprite;
    private Sprite hojaSprite;
    private Sprite ramaSprite;
    private Sprite hongo1Sprite;
    private Sprite puasSprite;
    private Sprite troncoSprite;
    private Sprite troncoTechoSprite;
    private Sprite troncoGrandeSprite;
    private Texture ramaImg;
    private Texture hongoImg;
    private Texture troncoQuebradizoImg;
    private Texture troncoGrandeImg;
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
    private Tronco troncoGrande2;       //El que sostiene al quebradizo suspendido

    //Inicio (hoja y rama)
    private HojaBasica hoja;
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
    private TroncoQuebradizo troncoSuspendido;
    private TroncoQuebradizo troncoSuspendido2;
    private TroncoQuebradizo troncoSuspendido3;

    private Manzana manzana1;
    private Manzana manzana2;
    private Manzana manzana3;

    //Joints
    private MouseJoint mouseJoint;
    private MouseJoint resorteJoint;
    private DistanceJoint hojaRamaJoint;
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
        ramaImg = new Texture(Gdx.files.internal("RamaIzquierdaParaHojasAbajo.png"));
        hongoImg = new Texture(Gdx.files.internal("rsz_hongosnaranja2.png"));
        troncoQuebradizoImg = new Texture(Gdx.files.internal("troncoQuebradizo.png"));
        troncoGrandeImg = new Texture(Gdx.files.internal("troncoDer.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondoMontanas.png"));

        gotaSprite = new Sprite(gotaImage);
        gotaMuertaSprite = new Sprite(gotaMuertaImage);
        gotafantasmaSprite = new Sprite(gotaFantasmaImage);
        puasSprite = new Sprite(puasImg);
        hojaSprite = new Sprite(hojaImg);
        ramaSprite = new Sprite(ramaImg);
        troncoSprite = new Sprite(troncoQuebradizoImg);
        hongo1Sprite = new Sprite(hongoImg);
        troncoGrandeSprite = new Sprite(troncoGrandeImg);

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
            world.step(1 / 45f, 6, 2);

        }
        this.actualizarSprites();

    }

    private void moveCamera(float y) {
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 - y, 0);
    }

    public void actualizarSprites() {

        //Dibuja los sprites
        //Pintar un tronco sin nada en esta posicion (camera.viewportWidth - 500) * WORLD_TO_BOX, (camera.viewportHeight - 175) * WORLD_TO_BOX
        this.game.batch.begin();
       // this.game.batch.draw(backgroundImage, 0, 0);
        paintSprite(hojaSprite, hoja);
        paintSprite(ramaSprite, rama);
        paintSprite(hongo1Sprite, hongo1);
        paintSprite(troncoSprite, tronco1);
        paintSprite(troncoSprite, tronco2);
        paintSprite(troncoSprite, tronco3);
        paintSprite(troncoSprite, tronco4);
        paintSprite(troncoSprite, tronco5);
        paintSprite(troncoSprite, tronco6);
        paintSprite(troncoSprite, tronco7);
        paintSprite(troncoGrandeSprite, troncoGrande2);
        paintSprite(troncoSprite,troncoSuspendido);
        paintSprite(puasSprite, pua1);
        paintSprite(troncoSprite, tronco8);

        if(!escuchadorColision.getMuerta()) {
            paintSprite(gotaSprite, enki);
        }else{
            paintSprite(gotaMuertaSprite, enki);
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
        this.game.batch.draw(sprite, objeto.getX(), objeto.getY(), objeto.getOrigen().x, objeto.getOrigen().y, objeto.getWidth(),
                objeto.getHeight(), sprite.getScaleX(), sprite.getScaleY(), objeto.getAngulo() * MathUtils.radiansToDegrees);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this.escuchadorColision);
        debugRenderer = new Box2DDebugRenderer();

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


        gotaSprite.setPosition((camera.viewportWidth - 920) * WORLD_TO_BOX, (camera.viewportHeight - 100) * WORLD_TO_BOX);
        hojaSprite.setPosition((camera.viewportWidth - 940) * WORLD_TO_BOX, (camera.viewportHeight - 250) * WORLD_TO_BOX);
        ramaSprite.setPosition((0 - ramaSprite.getWidth()/2) * WORLD_TO_BOX, (camera.viewportHeight - ramaSprite.getHeight()/2) * WORLD_TO_BOX );
        hongo1Sprite.setPosition((camera.viewportWidth - 810) * WORLD_TO_BOX, (camera.viewportHeight - 450) * WORLD_TO_BOX);
        puasSprite.setPosition( (camera.viewportWidth - 630) * WORLD_TO_BOX, (camera.viewportHeight - 420) * WORLD_TO_BOX);

        //Creacion de la gota
        enki = new Gota(world, (camera.viewportWidth - 920) * WORLD_TO_BOX, (camera.viewportHeight - 100) * WORLD_TO_BOX, gotaSprite.getWidth());

        //Creacion de la hoja
        hoja = new HojaBasica(world, (camera.viewportWidth - 940) * WORLD_TO_BOX, (camera.viewportHeight - 250) * WORLD_TO_BOX,
                hojaSprite.getWidth(), hojaSprite.getHeight());

        //Creacion de la rama
        rama = new Rama(world, (-ramaSprite.getWidth()/2) * WORLD_TO_BOX, (camera.viewportHeight - ramaSprite.getHeight()/2) * WORLD_TO_BOX,
                ramaSprite.getWidth(), ramaSprite.getHeight(), 2);

        //Creacion de hongos
        hongo1 = new Hongo(world, (camera.viewportWidth - 810) * WORLD_TO_BOX, (camera.viewportHeight - 450) * WORLD_TO_BOX,
                hongo1Sprite.getWidth() * 2, hongo1Sprite.getHeight() * 2, true);
//        hongo2 = new Hongo(world,  );

        //Creacion de puas
        pua1 = new Puas(world, (camera.viewportWidth - 630) * WORLD_TO_BOX, (camera.viewportHeight - 420) * WORLD_TO_BOX, puasSprite.getWidth()/2,
               puasSprite.getHeight()/2, false);

        //Creacion de tronco suspendido
        troncoSuspendido = new TroncoQuebradizo(world, (camera.viewportWidth - 400) * WORLD_TO_BOX, (camera.viewportHeight - 250) * WORLD_TO_BOX,
                troncoSprite.getWidth(), troncoSprite.getHeight(), 0, true);

        /**Creacion de troncos estructurales**/

        //Tronco inclinado
        tronco1 = new TroncoQuebradizo(world,(camera.viewportWidth - 900) * WORLD_TO_BOX, (camera.viewportHeight - 250) * WORLD_TO_BOX,
                troncoSprite.getWidth()/3, troncoSprite.getHeight()/3, 100, false);
        //Primer tronco vertical
        tronco2 = new TroncoQuebradizo(world, (camera.viewportWidth - 810) * WORLD_TO_BOX, (camera.viewportHeight - 330) * WORLD_TO_BOX,
                troncoSprite.getWidth()/3, troncoSprite.getHeight()/3, 40, false);
        //Primer tronco horizontal
        tronco3 = new TroncoQuebradizo(world, (camera.viewportWidth - 810) * WORLD_TO_BOX, (camera.viewportHeight - 450) * WORLD_TO_BOX,
                troncoSprite.getWidth()/3, troncoSprite.getHeight()/3, 0, false);
        //Segundo tronco vertical
        tronco4 = new TroncoQuebradizo(world, (camera.viewportWidth - 680) * WORLD_TO_BOX, (camera.viewportHeight - 330) * WORLD_TO_BOX,
                troncoSprite.getWidth()/3, troncoSprite.getHeight()/3, 40, false);
        //Segundo tronco horizontal
        tronco5 = new TroncoQuebradizo(world, (camera.viewportWidth - 680) * WORLD_TO_BOX, (camera.viewportHeight - 450) * WORLD_TO_BOX,
                troncoSprite.getWidth()/2, troncoSprite.getHeight()/2, 0, false);
        //Tercer tronco vertical
        tronco6 = new TroncoQuebradizo(world, (camera.viewportWidth - 500) * WORLD_TO_BOX, (camera.viewportHeight - 330) * WORLD_TO_BOX,
                troncoSprite.getWidth()/3, troncoSprite.getHeight()/3, 40, false);
        //Tronco que sostiene al troco suspendido
        troncoGrande2 = new Tronco(world, (camera.viewportWidth - 400) * WORLD_TO_BOX, (camera.viewportHeight - 100) * WORLD_TO_BOX,
                troncoGrandeSprite.getWidth(), troncoGrandeSprite.getHeight(), 0, true, false);
        //Segundo tronco inclinado
        tronco7 = new TroncoQuebradizo(world, (camera.viewportWidth - 300) * WORLD_TO_BOX, (camera.viewportHeight - 550) * WORLD_TO_BOX,
                troncoSprite.getWidth(), troncoSprite.getHeight(), -100, false);
        //Cuarto tronco vertical
        tronco8 = new TroncoQuebradizo(world, (camera.viewportWidth -300) * WORLD_TO_BOX, (camera.viewportHeight - 550) * WORLD_TO_BOX,
                troncoSprite.getWidth()/2, troncoSprite.getHeight()/2, 40, false);



        //Definicion del joint entre la hoja y la rama
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(rama.getRamaBody().getLocalPoint(new Vector2(0.8320f, 6.4319f)));
        jointDef.localAnchorB.set(hoja.getHojaBody().getLocalPoint(new Vector2(0.9920f, 6.4319f)));
        jointDef.bodyA = rama.getRamaBody();
        jointDef.bodyB = hoja.getHojaBody();
        jointDef.length = 0.05f;

        hojaRamaJoint = (DistanceJoint) world.createJoint(jointDef);

        //Definicion del primer joint entre el tronco suspendido y el tronco grande
        jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(troncoGrande2.getTroncoBody().getLocalPoint(new Vector2(7.088f, 7.536f)));
        jointDef.localAnchorB.set(troncoSuspendido.getTroncoBody().getLocalPoint(new Vector2(7.1359f, 5.4880f)));
        jointDef.bodyA = troncoGrande2.getTroncoBody();
        jointDef.bodyB = troncoSuspendido.getTroncoBody();
        jointDef.length = 2f;

        troncoTecho1_joint1 = (DistanceJoint)world.createJoint(jointDef);

        //Definicion del segundo joint entre el tronco suspendido y el tronco grande
        jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(troncoGrande2.getTroncoBody().getLocalPoint(new Vector2(10.016f, 7.536f)));
        jointDef.localAnchorB.set(troncoSuspendido.getTroncoBody().getLocalPoint(new Vector2(9.9679f, 5.4880f)));
        jointDef.bodyA = troncoGrande2.getTroncoBody();
        jointDef.bodyB = troncoSuspendido.getTroncoBody();
        jointDef.length = 2f;

        troncoTecho1_joint2 = (DistanceJoint)world.createJoint(jointDef);

        //MouseJoint para resorte de hoja
        MouseJointDef md = new MouseJointDef();
        md.bodyA = rama.getRamaBody();
        md.bodyB = hoja.getHojaBody();
        md.maxForce = 500 * hoja.getHojaBody().getMass();
        md.target.set(hoja.getX()*WORLD_TO_BOX, hoja.getY()*WORLD_TO_BOX);
        resorteJoint = (MouseJoint) world.createJoint(md);

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
        groundEdge.set(-180 * WORLD_TO_BOX, -1 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, -1 * WORLD_TO_BOX);
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

    //Para el arrastre de objetos de juego
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();

    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {

            if (fixture.getBody() == troncoSuspendido.getTroncoBody() && troncoTecho1_joint1 != null) {
                world.destroyJoint(troncoTecho1_joint1);
                troncoTecho1_joint1 = null;
            }

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
