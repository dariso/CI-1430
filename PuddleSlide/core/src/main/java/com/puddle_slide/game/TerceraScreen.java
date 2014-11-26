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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

public class TerceraScreen extends InputAdapter implements Screen{

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

    private Sprite tronco1_sprite_Kalam;
    private Sprite tronco2_sprite_Kalam;
    private Sprite hojaSprite;
    private Sprite ramaSprite;
    private Sprite hongoSprite;

    private Sprite gotaMuertaSprite;
    private Texture gotaImage;
    private Texture gotaMuertaImage;
    private Texture gotaFantasmaImage;
    private Texture puasImg;

    private Texture tronco1_Img_Kalam;
    private Texture tronco2_Img_Kalam;
    private Texture hojaImg;
    private Texture ramaImg;
    private Texture hongoImg;

    private Texture backgroundImage;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private float vel = 10;
    private float deltaAcumulado = 0;
    private float acumuladorCamara = 0;
    private Body ground;
    private Gota enki;
    private Puas pua;

    private Tronco tronco1_kalam;
    private Tronco tronco2_kalam;
    private HojaBasica hoja;
    private Rama rama;
    private Hongo hongo;

    private DistanceJoint jointPuasTronco_Kalam;
    private DistanceJoint hojaRamaJoint;
    private MouseJoint mouseJoint;
    private MouseJoint pruebaResorteJoint;


    boolean PAUSE = false;
    float volar = (float) 0.01;
    MyContactListener escuchadorColision;
    SoundControl sonido;
    public TerceraScreen(final com.puddle_slide.game.Puddle_Slide elJuego) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty1.png"));
        puasImg = new Texture (Gdx.files.internal("puasP.png"));
        gotaFantasmaImage =  new Texture (Gdx.files.internal("fantasmita.png"));
        gotaMuertaImage = new Texture(Gdx.files.internal("gotaM.png"));
        backgroundImage = new Texture(Gdx.files.internal("fondo3.png"));

        tronco1_Img_Kalam = new Texture(Gdx.files.internal("troncoQuebradizo.png"));
        tronco2_Img_Kalam = new Texture(Gdx.files.internal("troncoIzq.png"));
        hojaImg = new Texture(Gdx.files.internal("hoja2.png"));
        ramaImg = new Texture(Gdx.files.internal("RamaIzquierdaParaHojas.png"));
        hongoImg = new Texture(Gdx.files.internal("hongosNaranja2.png"));

        hojaSprite = new Sprite(hojaImg);
        ramaSprite = new Sprite(ramaImg);
        hongoSprite = new Sprite(hongoImg);

        gotaSprite = new Sprite(gotaImage);
        gotaMuertaSprite = new Sprite(gotaMuertaImage);
        gotafantasmaSprite = new Sprite(gotaFantasmaImage);
        puasSprite = new Sprite(puasImg);

        gotaSprite.setPosition((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        gotaMuertaSprite.setPosition(Gdx.graphics.getWidth()/2 *WORLD_TO_BOX , Gdx.graphics.getHeight() * WORLD_TO_BOX);

        puasSprite.setPosition(400* WORLD_TO_BOX,400* WORLD_TO_BOX);


        tronco1_sprite_Kalam = new Sprite(tronco1_Img_Kalam);
        tronco2_sprite_Kalam = new Sprite(tronco2_Img_Kalam);


        filehandle = Gdx.files.internal("skins/menuSkin.json");
        textura = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin = new Skin(filehandle,textura);
        buttonPause = new TextButton("Pausa", skin);
        buttonRegresar = new TextButton("Menu", skin);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        sonido = SoundControl.getInstancia();
        escuchadorColision = MyContactListener.getInstancia(sonido);
    }
    private Vector2 vec = new Vector2();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        Matrix4 cameraCopy = camera.combined.cpy();

        //Si no esta en pausa actualiza las posiciones
        if (!PAUSE) {
            deltaAcumulado += delta;
            //para pruebas, espera dos segundos antes de mover la camara


/*

            if (deltaAcumulado > 2) {
                //pasar a metodo externo que mueve camara 768 pixeles para abajo
                //prueba mueve 600 pixeles para abajo despues de dos segundos transcurridos.
                acumuladorCamara += 3;
                System.out.println(acumuladorCamara);
                if (acumuladorCamara < 1534 ) {
                    moveCamera(acumuladorCamara);


                }
            }

*/

            camera.update();
            debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
            world.step(1 / 60f, 6, 2);
            //mapRenderer.setView(camera);
            //mapRenderer.render();

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

        puasSprite.setPosition(pua.getX(), pua.getY());
        puasSprite.setRotation(pua.getAngulo() * MathUtils.radiansToDegrees);


        hojaSprite.setPosition(hoja.getX(), hoja.getY());
        hojaSprite.setOrigin(hoja.getOrigen().x, hoja.getOrigen().y);
        hojaSprite.setRotation(hoja.getAngulo() * MathUtils.radiansToDegrees);

        hongoSprite.setPosition(hongo.getX(), hongo.getY());
        hongoSprite.setOrigin(hongo.getOrigen().x, hongo.getOrigen().y);
        hongoSprite.setRotation(hongo.getAngulo() * MathUtils.radiansToDegrees);

        ramaSprite.setPosition(rama.getX(), rama.getY());

        //Dibuja los sprites
        this.game.batch.begin();
       // this.game.batch.draw(backgroundImage, 0,0);
        // this.game.batch.draw(backgroundImage, 0,-camera.viewportHeight*2);

        this.game.batch.draw(hojaSprite, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getOriginX(), hojaSprite.getOriginY(), hojaSprite.getWidth(),
                hojaSprite.getHeight(), hojaSprite.getScaleX(), hojaSprite.getScaleY(), hojaSprite.getRotation());
        this.game.batch.draw(ramaSprite, ramaSprite.getX(), ramaSprite.getY());

        this.game.batch.draw(puasSprite, puasSprite.getX(), puasSprite.getY(), pua.getOrigen().x, pua.getOrigen().y, puasSprite.getWidth()/2,
                puasSprite.getHeight()/2, puasSprite.getScaleX(), puasSprite.getScaleY(), puasSprite.getRotation());

        this.game.batch.draw(tronco1_sprite_Kalam, tronco1_kalam.getX(), tronco1_kalam.getY(), tronco1_kalam.getOrigen().x, tronco1_kalam.getOrigen().y, tronco1_sprite_Kalam.getWidth()/2,
                tronco1_sprite_Kalam.getHeight()/2 , tronco1_sprite_Kalam.getScaleX(), tronco1_sprite_Kalam.getScaleY(), tronco1_kalam.getAngulo() * MathUtils.radiansToDegrees);

        this.game.batch.draw(tronco2_sprite_Kalam, tronco2_kalam.getX(), tronco2_kalam.getY(), tronco2_kalam.getOrigen().x, tronco2_kalam.getOrigen().y, tronco2_sprite_Kalam.getWidth()/2,
                tronco2_sprite_Kalam.getHeight()/2 , tronco2_sprite_Kalam.getScaleX(), tronco2_sprite_Kalam.getScaleY(), tronco2_kalam.getAngulo() * MathUtils.radiansToDegrees);

        this.game.batch.draw(hongoSprite, hongoSprite.getX(), hongoSprite.getY(), hongo.getOrigen().x, hongo.getOrigen().y, 259,
                250, hongoSprite.getScaleX(), hongoSprite.getScaleY(), hongoSprite.getRotation());

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

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this.escuchadorColision);
        debugRenderer = new Box2DDebugRenderer();

        //TiledMap map = new TmxMapLoader().load("mapas/nivel.tmx");

       // mapRenderer = new OrthogonalTiledMapRenderer(map);



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

        gotaSprite.setPosition((camera.viewportWidth - 825) * WORLD_TO_BOX, (camera.viewportHeight - 300) * WORLD_TO_BOX);
        hojaSprite.setPosition((camera.viewportWidth - 900) * WORLD_TO_BOX, (camera.viewportHeight - 400) * WORLD_TO_BOX);
        ramaSprite.setPosition(0, (camera.viewportHeight - 300) * WORLD_TO_BOX);
        hongoSprite.setPosition((camera.viewportWidth - 250) * WORLD_TO_BOX, 5* WORLD_TO_BOX);


        //Creacion de la hoja
        hoja = new HojaBasica(world, hojaSprite.getX(), hojaSprite.getY(), hojaSprite.getWidth(), hojaSprite.getHeight());


        //Creacion de la rama para hojas
        rama = new Rama(world, ramaSprite.getX(), ramaSprite.getY(), ramaSprite.getWidth(), ramaSprite.getHeight(), 1);

        //Definicion del joint entre la hoja y la rama
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.localAnchorA.set(rama.getRamaBody().getLocalPoint(new Vector2(1.3920f, 5.2f)));
        jointDef.localAnchorB.set(hoja.getHojaBody().getLocalPoint(new Vector2( 9.392f, 1.7279998f)));
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
        FixtureDef fixtureDefBloque = new FixtureDef();
        groundDef.position.set(new Vector2(0,0));
        groundDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(groundDef);

        //definicion borde Izquierdo
        groundEdge.set(-1 * WORLD_TO_BOX,-1536 * WORLD_TO_BOX,-1 * WORLD_TO_BOX, camera.viewportHeight * WORLD_TO_BOX);
        fixtureDefIzq.shape = groundEdge;
        fixtureDefIzq.density = 0;
        ground.createFixture(fixtureDefIzq);
        fixtureDefIzq.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefIzq.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA|FigureId.BIT_HONGO;
        ground.createFixture(fixtureDefIzq).setUserData("borde_izq");

        //definicion Piso

        groundEdge.set(-1 * WORLD_TO_BOX, 5 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, 5 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA|FigureId.BIT_HONGO;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");


        //definicion Piso

        groundEdge.set(400 * WORLD_TO_BOX, 400 * WORLD_TO_BOX, 600 * WORLD_TO_BOX, 400 * WORLD_TO_BOX);
        fixtureDefBloque.shape = groundEdge;
        fixtureDefBloque.density = 0;
        ground.createFixture(fixtureDefBloque);
        fixtureDefBloque.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefBloque).setUserData("borde_piso");

        //definicion borde Derecho
        groundEdge.set((camera.viewportWidth+1) * WORLD_TO_BOX, -1536*WORLD_TO_BOX, (camera.viewportWidth+1)*WORLD_TO_BOX, camera.viewportHeight*WORLD_TO_BOX);
        fixtureDefDer.shape = groundEdge;
        fixtureDefDer.density = 0;
        ground.createFixture(fixtureDefDer);
        fixtureDefDer.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefDer.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefDer).setUserData("borde_der");

        groundEdge.dispose();

        //Creacion del puas
        pua = new Puas(world, puasSprite.getX(), puasSprite.getY(), puasSprite.getWidth()/2, puasSprite.getHeight()/2, false);

        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Creación tronco
        tronco1_kalam = new Tronco(world, (camera.viewportWidth - 624) * WORLD_TO_BOX, (camera.viewportHeight - 50) * WORLD_TO_BOX, tronco1_sprite_Kalam.getWidth()/2, tronco1_sprite_Kalam.getHeight()/2, 0.1f ,false,false);
        tronco2_kalam = new Tronco(world, (camera.viewportWidth - 400) * WORLD_TO_BOX, (camera.viewportHeight - 450) * WORLD_TO_BOX, tronco2_sprite_Kalam.getWidth()/2, tronco2_sprite_Kalam.getHeight()/2, 0.34f, false, false);

        //Creacion del hongo
        hongo = new Hongo(world, hongoSprite.getX(), hongoSprite.getY(), hongoSprite.getWidth(),hongoSprite.getHeight());





        table.add(buttonPause).size(140,40).padTop(-160).padLeft(450).row();
        table.add(buttonRegresar).size(140,40).padTop(-30).padBottom(250).padLeft(450);
        table.setFillParent(true);
        stage.addActor(table);
       // Gdx.input.setInputProcessor(stage);

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
        puasImg.dispose();
        backgroundImage.dispose();
        gotaMuertaImage.dispose();
        gotaFantasmaImage.dispose();
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


