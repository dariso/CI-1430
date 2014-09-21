package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class GameScreen implements Screen {

    final Puddle_Slide game;
    private OrthographicCamera camera;
    private Texture gotaImage;
    private Texture backgroundImage;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //codigo ejemplo box2d
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private BodyDef groundDef;
    private Body groundBody;
    private BodyDef playerDef;
    private Body playerBody;



    public GameScreen(final Puddle_Slide elJuego) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
        backgroundImage = new Texture(Gdx.files.internal("background.png"));

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);

        //codigo ejemplo box2d
        Matrix4 cameraCopy = camera.combined.cpy();
        debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
        world.step(1/60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        ///codigo ejemplo box2d
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        groundDef = new BodyDef();
        groundDef.position.set(new Vector2((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX));
        groundBody = world.createBody(groundDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX);
        groundBody.createFixture(groundShape, 0f);
        groundShape.dispose();
        playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(new Vector2(100 * WORLD_TO_BOX, 400 * WORLD_TO_BOX));
        playerBody = world.createBody(playerDef);
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(50 * WORLD_TO_BOX, 50 * WORLD_TO_BOX);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = playerBody.createFixture(fixtureDef);
        playerShape.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gotaImage.dispose();
        backgroundImage.dispose();
    }

}
