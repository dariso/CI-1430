package com.puddle_slide.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class GameScreen implements Screen {

    final Puddle_Slide game;
    private OrthographicCamera camera;
    private Sprite gotaSprite;
    private Sprite hojaSprite;
    private Texture gotaImage;
    private Texture hojaImg;
    private Texture backgroundImage;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    //Objetos del mundo
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private BodyDef hojaDef;
    private Body hojaBody;
    private BodyDef gotaDef;
    private Body gotaBody;



    public GameScreen(final Puddle_Slide elJuego) {

        this.game = elJuego;
        gotaImage = new Texture(Gdx.files.internal("gotty.png"));
        hojaImg = new Texture (Gdx.files.internal("bucket.png"));
        backgroundImage = new Texture(Gdx.files.internal("background.png"));

        gotaSprite = new Sprite(gotaImage);
        hojaSprite = new Sprite(hojaImg);

        gotaSprite.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);
        hojaSprite.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4, 0);

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0,0,1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);

        Matrix4 cameraCopy = camera.combined.cpy();
        debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
        world.step(1/60f, 6, 2);

        gotaSprite.setPosition(gotaBody.getPosition().x, gotaBody.getPosition().y);
        this.game.batch.begin();
        this.game.batch.draw(backgroundImage, 0, 0);
        this.game.batch.draw(hojaSprite, hojaSprite.getX(), hojaSprite.getY());
        this.game.batch.draw(gotaSprite, gotaSprite.getX(), gotaSprite.getY());
        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -98), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Definicion de la hoja
        hojaDef = new BodyDef();
        hojaDef.position.set(hojaSprite.getX(), hojaSprite.getY());
        hojaBody = world.createBody(hojaDef);
        PolygonShape hojaShape = new PolygonShape();
        hojaShape.setAsBox(hojaSprite.getWidth(), hojaSprite.getY());
        hojaBody.createFixture(hojaShape, 0f);
        hojaShape.dispose();

        //Definicion de la gota
        gotaDef = new BodyDef();
        gotaDef.type = BodyDef.BodyType.DynamicBody;
        gotaDef.position.set(gotaSprite.getX(), gotaSprite.getY());
        gotaBody = world.createBody(gotaDef);
        CircleShape gotaShape = new CircleShape();
        gotaShape.setRadius(gotaSprite.getWidth()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = gotaShape;
        fixtureDef.density = 999.97f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.4f;
        gotaBody.createFixture(fixtureDef);
        gotaShape.dispose();
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
