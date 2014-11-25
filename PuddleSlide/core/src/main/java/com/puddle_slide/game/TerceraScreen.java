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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
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

    private Sprite gotaMuertaSprite;
    private Texture gotaImage;
    private Texture gotaMuertaImage;
    private Texture gotaFantasmaImage;
    private Texture puasImg;

    private Texture tronco1_Img_Kalam;
    private Texture tronco2_Img_Kalam;

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

        tronco1_Img_Kalam = new Texture(Gdx.files.internal("troncoDer.png"));
        tronco2_Img_Kalam = new Texture(Gdx.files.internal("troncoIzq.png"));

        gotaSprite = new Sprite(gotaImage);
        gotaMuertaSprite = new Sprite(gotaMuertaImage);
        gotafantasmaSprite = new Sprite(gotaFantasmaImage);
        puasSprite = new Sprite(puasImg);
        gotaSprite.setPosition((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        gotaMuertaSprite.setPosition(Gdx.graphics.getWidth()/2 *WORLD_TO_BOX , Gdx.graphics.getHeight() * WORLD_TO_BOX);
        puasSprite.setPosition(1,0);

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

            System.out.println("X: "+pua.getX()+" Y: "+pua.getY());

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

        tronco1_sprite_Kalam.setPosition(tronco1_kalam.getX(), tronco1_kalam.getY());
        tronco1_sprite_Kalam.setRotation(pua.getAngulo() * MathUtils.radiansToDegrees);

        tronco2_sprite_Kalam.setPosition(tronco2_kalam.getX(), tronco2_kalam.getY());
        tronco2_sprite_Kalam.setRotation(pua.getAngulo() * MathUtils.radiansToDegrees);




        //Dibuja los sprites
        this.game.batch.begin();
       // this.game.batch.draw(backgroundImage, 0,0);
        // this.game.batch.draw(backgroundImage, 0,-camera.viewportHeight*2);
        this.game.batch.draw(puasSprite, puasSprite.getX(), puasSprite.getY(), pua.getOrigen().x, pua.getOrigen().y, puasSprite.getWidth(),
                puasSprite.getHeight(), puasSprite.getScaleX(), puasSprite.getScaleY(), puasSprite.getRotation());

        this.game.batch.draw(tronco1_sprite_Kalam, tronco1_kalam.getX(), tronco1_kalam.getY(), tronco1_kalam.getOrigen().x, tronco1_kalam.getOrigen().y, tronco1_sprite_Kalam.getWidth()/2,
                tronco1_sprite_Kalam.getHeight()/2 , tronco1_sprite_Kalam.getScaleX(), tronco1_sprite_Kalam.getScaleY(), tronco1_kalam.getAngulo() * MathUtils.radiansToDegrees);

        this.game.batch.draw(tronco2_sprite_Kalam, tronco2_kalam.getX(), tronco2_kalam.getY(), tronco2_kalam.getOrigen().x, tronco2_kalam.getOrigen().y, tronco2_sprite_Kalam.getWidth()/2,
                tronco2_sprite_Kalam.getHeight()/2 , tronco2_sprite_Kalam.getScaleX(), tronco2_sprite_Kalam.getScaleY(), tronco2_kalam.getAngulo() * MathUtils.radiansToDegrees);


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
        groundEdge.set(-1 * WORLD_TO_BOX,-1536 * WORLD_TO_BOX,-1 * WORLD_TO_BOX, camera.viewportHeight * WORLD_TO_BOX);
        fixtureDefIzq.shape = groundEdge;
        fixtureDefIzq.density = 0;
        ground.createFixture(fixtureDefIzq);
        fixtureDefIzq.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefIzq.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefIzq).setUserData("borde_izq");

        //definicion Piso

        groundEdge.set(-1 * WORLD_TO_BOX, 5 * WORLD_TO_BOX, camera.viewportWidth * WORLD_TO_BOX, 5 * WORLD_TO_BOX);
        fixtureDefPiso.shape = groundEdge;
        fixtureDefPiso.density = 0;
        ground.createFixture(fixtureDefPiso);
        fixtureDefPiso.filter.categoryBits = FigureId.BIT_BORDE;
        fixtureDefPiso.filter.maskBits = FigureId.BIT_PUAS|FigureId.BIT_HOJA|FigureId.BIT_GOTA;
        ground.createFixture(fixtureDefPiso).setUserData("borde_piso");

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
        pua = new Puas(world, puasSprite.getX(), puasSprite.getY(), puasSprite.getWidth(), puasSprite.getHeight(), false);

        //Creacion de la gota
        enki = new Gota(world, gotaSprite.getX(), gotaSprite.getY(), gotaSprite.getWidth());

        //Creación tronco
        tronco1_kalam = new Tronco(world, (camera.viewportWidth - 900) * WORLD_TO_BOX, (camera.viewportHeight - 250) * WORLD_TO_BOX, tronco1_sprite_Kalam.getWidth()/2, tronco1_sprite_Kalam.getHeight()/2, -0.34f, true, false);
        tronco2_kalam = new Tronco(world, (camera.viewportWidth - 400) * WORLD_TO_BOX, (camera.viewportHeight - 450) * WORLD_TO_BOX, tronco1_sprite_Kalam.getWidth()/2, tronco1_sprite_Kalam.getHeight()/2, 0.34f, false, false);


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


