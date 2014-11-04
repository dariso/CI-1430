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
public class Manzana{

    private Body manzanaBody;
    private Vector2 puntoRef;
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

        BodyDef manzanaDef = new BodyDef();
        manzanaDef.type = BodyDef.BodyType.DynamicBody;
        manzanaDef.position.set(x, y);
        manzanaBody = world.createBody(manzanaDef);

        PolygonShape polygonShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();

        //Define la cetegoria de objeto a la que pertenece
        fixtureDef.filter.categoryBits = FigureId.BIT_MANZANA;

        //Define los objetos con los que debe colisionar
        fixtureDef.filter.maskBits = FigureId.BIT_GOTA | FigureId.BIT_BORDE;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 0.42f;
        fixtureDef.restitution = 0.5f;


        bodyEditorLoader.attachFixture( manzanaBody,"manzana",  fixtureDef, ancho * WORLD_TO_BOX);
        puntoRef = bodyEditorLoader.getOrigin("manzana", ancho * WORLD_TO_BOX);

    }

    public float getX(){
        return manzanaBody.getPosition().x * BOX_TO_WORLD;
    }

    public float getY(){
        return manzanaBody.getPosition().y * BOX_TO_WORLD;
    }

    public float getAngulo(){
        return manzanaBody.getAngle();
    }

    public Vector2 getOrigen(){
        return puntoRef;
    }

    public void mover(Vector2 v){
        manzanaBody.applyForceToCenter(v, true);
    }

    public float getMasa(){
        return manzanaBody.getMass();
    }

    public Body getManzanaBody() {
        return manzanaBody;
    }
}
