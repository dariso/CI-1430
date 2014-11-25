package com.puddle_slide.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.FixtureDef;
        import com.badlogic.gdx.physics.box2d.PolygonShape;
        import com.badlogic.gdx.physics.box2d.World;



/**
 * Created by Meli.
 */
public class Manzana implements ObjetoJuego{

    private Body manzanaBody;
    private Vector2 puntoRef;
    private float largo;
    private float ancho;
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    /**
     * Constructor de la manzana
     * @param world Mundo en el que se dibujara la manzana
     * @param x Posicion en el eje x en el que se dibujara la manzana
     * @param y Posicion en el eje y en el que se dibujara la manzana
     * @param ancho Ancho del sprite de la manzana
     * @param alto Alto del sprite de la manzana
     * */
    public Manzana(World world, float x, float y, float ancho, float alto) {
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("Shapes/manzana.json"));

        largo = alto;
        this.ancho = ancho;

        BodyDef manzanaDef = new BodyDef();
        manzanaDef.type = BodyDef.BodyType.DynamicBody;
        manzanaDef.position.set(x, y);
        manzanaBody = world.createBody(manzanaDef);

        PolygonShape polygonShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_MANZANA;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_BORDE | FigureId.BIT_PUAS | FigureId.BIT_TRONCO;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.5f;


        bodyEditorLoader.attachFixture( manzanaBody,"manzana",  fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("manzana", ancho * WORLD_TO_BOX);

    }

    @Override
    public float getX(){
        return manzanaBody.getPosition().x * BOX_TO_WORLD;
    }

    @Override
    public float getY(){
        return manzanaBody.getPosition().y * BOX_TO_WORLD;
    }

    @Override
    public float getWidth() {
        return ancho;
    }

    @Override
    public float getHeight() {
        return largo;
    }

    @Override
    public float getAngulo(){
        return manzanaBody.getAngle();
    }

    @Override
    public Vector2 getOrigen(){
        return puntoRef;
    }

    @Override
    public float getMasa(){
        return manzanaBody.getMass();
    }

    public Body getManzanaBody() {
        return manzanaBody;
    }
}
