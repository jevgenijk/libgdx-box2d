package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.InputHandler;
import com.aivars.firstgame.handlers.StateHandler;
import com.aivars.firstgame.model.BodyUserData;
import com.aivars.firstgame.utils.RevoluteJoint;
import com.aivars.firstgame.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.Constants.*;
import static com.aivars.firstgame.utils.Utils.scale;

public class GameState extends State {

    /**
     * TODO:
     * 1. Top highscore
     * 2. Resetting game
     * 3. Styling/images
     * 4. Randomize obstacle position - inside/outside
     * 5. Randomize obstacle distance
     * . Refactoring
     * */

    private static boolean isBallOutside = true;
    private int lastAngle = 360;
    private static World world;
    private static RevoluteJoint revoluteJoint;
    private static Body pivot;
    private static Body spike;
    private static Joint joint;
    private ContactHandler contactHandler = new ContactHandler();
    private InputHandler inputHandler = new InputHandler();
    private BitmapFont font = new BitmapFont();
    private Box2DDebugRenderer debugger;
    private Texture ballTexture;
    private Sprite sprite;
    private int circleCount = -1;

    public GameState(StateHandler gameStateHandler) {
        super(gameStateHandler);
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(contactHandler);
        debugger = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(inputHandler);

        pivot = createCircle(world, BodyDef.BodyType.StaticBody, scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2), BIG_CIRCLE_RADIUS);
        spike = createCircle(world, BodyDef.BodyType.DynamicBody, 0, 0, SMALL_CIRCLE_RADIUS);
        MassData massData = new MassData();
        massData.I = (float) 1;
        spike.setMassData(massData);
        createJoint(BALL_OUTER_LENGTH);

        ballTexture = new Texture("ball.png");
        sprite = new Sprite(ballTexture);
    }

    public static boolean isBallOutside() {
        return isBallOutside;
    }

    public static void setBallOutside(boolean isOutside) {
        isBallOutside = isOutside;
    }

    public static void createJoint(int scaleB) {
        if (joint != null) {
            world.destroyJoint(joint);
        }

        revoluteJoint = new RevoluteJoint(pivot, spike, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, 90);
        joint = revoluteJoint.CreateJoint(world);
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);

        // Remove obstacles
        Array<Body> bodies = contactHandler.getRemovableBodies();
        for (int i = 0; i < bodies.size; i++) {
            world.destroyBody(bodies.get(i));
        }
        bodies.clear();

        //Add new obstacles
        double spikeAngle = Utils.calcRotationAngleInDegrees(pivot.getPosition().x, pivot.getPosition().y, spike.getPosition().x, spike.getPosition().y);
        //Resetting circle angle if current spike angle is somewhere between 0 and 5 degrees
        if (lastAngle >= 360 && spikeAngle > 0 && spikeAngle < 5) {
            lastAngle = 0;
            circleCount++;
        }

        if (lastAngle < spikeAngle) {
            int x = (int) (pivot.getPosition().x * Constants.PPM + (BIG_CIRCLE_RADIUS + 10) * Math.cos(spikeAngle * Math.PI / 180F));
            int y = (int) (pivot.getPosition().y * Constants.PPM + (BIG_CIRCLE_RADIUS + 10) * Math.sin(spikeAngle * Math.PI / 180F));
            int sensorX = (int) (pivot.getPosition().x * Constants.PPM + (BIG_CIRCLE_RADIUS - 10) * Math.cos(spikeAngle * Math.PI / 180F));
            int sensorY = (int) (pivot.getPosition().y * Constants.PPM + (BIG_CIRCLE_RADIUS - 10) * Math.sin(spikeAngle * Math.PI / 180F));

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(scale(x), scale(y));
            bodyDef.angle = (float) Math.toRadians(spikeAngle + 90);
            Body body = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 3;
            fixtureDef.restitution = 0.1f;
            fixtureDef.friction = 0.8f;
            PolygonShape poly = new PolygonShape();
            poly.setAsBox(scale(3), scale(10));
            fixtureDef.shape = poly;
            body.createFixture(fixtureDef);

            BodyDef sensorBodyDef = new BodyDef();
            sensorBodyDef.type = BodyDef.BodyType.StaticBody;
            sensorBodyDef.position.set(scale(sensorX), scale(sensorY));
            sensorBodyDef.angle = (float) Math.toRadians(spikeAngle + 90);
            Body sensorBody = world.createBody(sensorBodyDef);
            fixtureDef.isSensor = true;
            sensorBody.createFixture(fixtureDef);
            fixtureDef.shape.dispose();
            sensorBody.setUserData(new BodyUserData(body));

            lastAngle = (int) (spikeAngle + 30);
        }

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        font.draw(application.getSpriteBatch(), "Count: " + (circleCount == -1 ? 0 : circleCount), 30, Constants.HEIGHT - 30);
        int circleSize = 16;
        spriteBatch.draw(sprite, spike.getPosition().x * Constants.PPM - (circleSize / 2), spike.getPosition().y * Constants.PPM - (circleSize / 2), circleSize, circleSize);
        spriteBatch.end();

        debugger.render(world, camera.combined.scl(Constants.PPM));
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        debugger.dispose();
        world.dispose();
        ballTexture.dispose();
    }

    public Body createCircle(World world, BodyDef.BodyType bodyType, float posX, float posY,
                             float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(posX, posY);
        bodyDef.angle = 0;

        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.restitution = 1;
        fixtureDef.friction = 0;
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.setRadius(scale(radius));

        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();
        return body;
    }

}
