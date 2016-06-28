package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.GameStateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class PlayState extends GameState {

    Body boxBody;
    private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer debuger;


    public PlayState(GameStateHandler gameStateHandler) {
        super(gameStateHandler);
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new ContactHandler());
        debuger = new Box2DDebugRenderer();

        /*BodyDef groundDef = new BodyDef();
        groundDef.position.set(160 / Constants.PTM, 120 / Constants.PTM);
        groundDef.type = BodyDef.BodyType.StaticBody;
        Body groundBody = world.createBody(groundDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(500 / Constants.PTM, 5 / Constants.PTM);
        FixtureDef groundFDef = new FixtureDef();
        groundFDef.shape = groundShape;
        groundBody.createFixture(groundFDef).setUserData("ground");*/


        /*BodyDef boxDef = new BodyDef();
        boxDef.position.set(160 / Constants.PTM, 200 / Constants.PTM);
        boxDef.type = BodyDef.BodyType.DynamicBody;
        boxBody = world.createBody(boxDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(5 / Constants.PTM, 5 / Constants.PTM);
        FixtureDef boxFDef = new FixtureDef();
        boxFDef.shape = boxShape;
        boxBody.createFixture(boxFDef).setUserData("box");*/

        Body bigCircle = createCircle(scale(50), scale(Constants.WIDTH/2),scale(Constants.HEIGHT/2));
        bigCircle.setType(BodyDef.BodyType.StaticBody);

        Body anchorPoint = createBox(scale(1), scale(1), scale(Constants.WIDTH/2), scale(Constants.HEIGHT/2));
        anchorPoint.setType(BodyDef.BodyType.StaticBody);

        Body rotator = createBox(scale(4),scale(4),scale(Constants.WIDTH/2),scale(Constants.HEIGHT/2));
        rotator.setType(BodyDef.BodyType.DynamicBody);

        Body circle = createCircle(scale(4),scale(Constants.WIDTH/2+40),scale(Constants.HEIGHT/2+40));
        circle.setType(BodyDef.BodyType.KinematicBody);

      /*  RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(anchorPoint, rotator, anchorPoint.getWorldCenter());
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = 5;
        revoluteJointDef.maxMotorTorque = 1;

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.initialize(rotator, circle, rotator.getWorldCenter());

        world.createJoint(revoluteJointDef);
        world.createJoint(weldJointDef);*/


        cam.setToOrtho(false, scale(Constants.WIDTH), scale(Constants.HEIGHT));
    }

    private static FixtureDef createFixtureDefinition(final Shape shape, final float density) {
        final FixtureDef nodeFixtureDefinition = new FixtureDef();
        nodeFixtureDefinition.shape = shape;
        nodeFixtureDefinition.friction = 1;
        nodeFixtureDefinition.density = density;
        nodeFixtureDefinition.restitution = 0.1f;
        return nodeFixtureDefinition;
    }

    private float scale(int x) {
        return x / Constants.PTM;
    }

    private float scale(float x) {
        return x / Constants.PTM;
    }

    private Body createBox(float w, float h, float x, float y) {
        BodyDef nodeBodyDefinition = new BodyDef();
        nodeBodyDefinition.type = BodyDef.BodyType.DynamicBody;
        nodeBodyDefinition.position.set(50, 50);

        PolygonShape shape = new PolygonShape();
        float density = 1.0f;
        shape.setAsBox(w / 2.0f, h / 2.0f);

        Body body = world.createBody(nodeBodyDefinition);
        body.setUserData(this);
        body.setTransform(x, y, 0);
        final FixtureDef nodeFixtureDefinition = createFixtureDefinition(shape, density);

        body.createFixture(nodeFixtureDefinition);
        shape.dispose();

        return body;
    }

    private Body createCircle(float r, float x, float y) {
        BodyDef nodeBodyDefinition = new BodyDef();
        nodeBodyDefinition.type = BodyDef.BodyType.DynamicBody;
        nodeBodyDefinition.position.set(10, 10);

        CircleShape shape = new CircleShape();
        float density = 1.0f;
        shape.setRadius(r);

        Body body = world.createBody(nodeBodyDefinition);
        body.setUserData(this);
        body.setTransform(x, y, 0);
        final FixtureDef nodeFixtureDefinition = createFixtureDefinition(shape, density);

        body.createFixture(nodeFixtureDefinition);
        shape.dispose();

        return body;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            System.out.println("left");
            boxBody.applyForceToCenter(-10, 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            System.out.println("right");
            boxBody.applyForceToCenter(10, 0, true);
        }
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
        handleInput();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debuger.render(world, cam.combined);
        game.getSpriteBatch().begin();
        font.draw(game.getSpriteBatch(), "Amount: ", 0, 480);
        game.getSpriteBatch().end();
    }

    @Override
    public void dispose() {

    }
}
