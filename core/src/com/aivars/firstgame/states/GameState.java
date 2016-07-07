package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.Utils;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.StateHandler;
import com.aivars.firstgame.model.BodyUserData;
import com.aivars.firstgame.model.Obstacle;
import com.aivars.firstgame.test.RevoluteJoint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.aivars.firstgame.Utils.scale;

public class GameState extends State implements InputProcessor {

    public static List<Obstacle> obstacles = new ArrayList<Obstacle>();
    int bigCircleRadius = 120;
    int smallCircleRadius = 5;
    int spikeOuterLength = 130;
    int spikeInnerLength = 105;
    int lastAngle = 270;
    private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer debuger;
    private RevoluteJoint revoluteJoint;
    private Body pivot;
    private Body spike;
    private Joint joint;
    private boolean outside = true;
    private Texture ballTexture;
    private Sprite sprite;
    private int hitCount = 0;

    public GameState(StateHandler gameStateHandler) {
        super(gameStateHandler);
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new ContactHandler());
        debuger = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(this);

        pivot = createCircle(world, BodyDef.BodyType.StaticBody, scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2), bigCircleRadius);
        spike = createCircle(world, BodyDef.BodyType.DynamicBody, 0,0, smallCircleRadius);
        MassData massData = new MassData();
        massData.I = (float) 1;
        spike.setMassData(massData);
        createJoint(spikeOuterLength);

        ballTexture = new Texture("ball.png");
        sprite = new Sprite(ballTexture);

    }

    private void createJoint(int scaleB) {
        if (joint != null) {
            world.destroyJoint(joint);
        }

        revoluteJoint = new RevoluteJoint(pivot, spike, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, 90);
        joint = revoluteJoint.CreateJoint(world);
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

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
        disposeBodies();

        double spikeAngle = Utils.calcRotationAngleInDegrees(pivot.getPosition().x, pivot.getPosition().y, spike.getPosition().x, spike.getPosition().y);
        System.out.println(spikeAngle);
        if (lastAngle >= 360 && spikeAngle > 0 && spikeAngle < 5) {
            lastAngle = 0;
        }

        if (lastAngle < spikeAngle) {
            int x = (int) (pivot.getPosition().x * Constants.PPM + (bigCircleRadius + 10) * Math.cos(spikeAngle * Math.PI / 180F));
            int y = (int) (pivot.getPosition().y * Constants.PPM + (bigCircleRadius + 10) * Math.sin(spikeAngle * Math.PI / 180F));
            int sensorX = (int) (pivot.getPosition().x * Constants.PPM + (bigCircleRadius - 10) * Math.cos(spikeAngle * Math.PI / 180F));
            int sensorY = (int) (pivot.getPosition().y * Constants.PPM + (bigCircleRadius - 10) * Math.sin(spikeAngle * Math.PI / 180F));

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
            body.setUserData(new BodyUserData(sensorBody));

            lastAngle = (int) (spikeAngle + 30);
        }

    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        font.draw(application.getSpriteBatch(), "Count: " + hitCount, 30, Constants.HEIGHT - 30);
        int circleSize = 16;
        spriteBatch.draw(sprite, spike.getPosition().x * Constants.PPM - (circleSize / 2), spike.getPosition().y * Constants.PPM - (circleSize / 2), circleSize, circleSize);
        spriteBatch.end();

        debuger.render(world, camera.combined.scl(Constants.PPM));
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void disposeBodies() {
        Array<Body> worldBodies = new Array<Body>();
        world.getBodies(worldBodies);

        Iterator<Body> i = worldBodies.iterator();
        Body node = i.next();
        while (i.hasNext()) {
            Body oBj = node;
            node = i.next();
            BodyUserData data = (BodyUserData) oBj.getUserData();
            if (data != null) {
                BodyUserData sensorData = (BodyUserData) data.getObstacle().getUserData();
                if (sensorData.isDisposable()) {
                    world.destroyBody(oBj);
                    world.destroyBody(data.getObstacle());
                }
            }
        }
    }

    @Override
    public void dispose() {
        debuger.dispose();
        world.dispose();
        ballTexture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            System.out.println("left");

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            System.out.println("right");
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (outside) {
            createJoint(spikeInnerLength);
        } else {
            createJoint(spikeOuterLength);
        }

        outside = !outside;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
