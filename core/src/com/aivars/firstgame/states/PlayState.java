package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.GameStateHandler;
import com.aivars.firstgame.test.RevoluteJoint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.aivars.firstgame.Utils.scale;

public class PlayState extends GameState implements InputProcessor {

    private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer debuger;
    private RevoluteJoint revoluteJoint;
    private Body pivot;
    private Body spike;
    private Joint joint;
    private boolean outside = true;
    private int count = 0;

    public PlayState(GameStateHandler gameStateHandler) {
        super(gameStateHandler);
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new ContactHandler());
        debuger = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(this);

        pivot = CreateCircleBody(world, BodyDef.BodyType.StaticBody, scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2), 60);
        spike = CreateCircleBody(world, BodyDef.BodyType.DynamicBody, scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2), 5);
        MassData massData = new MassData();
        massData.I = (float) 1;
        spike.setMassData(massData);
        createJoint(70);

        cam.setToOrtho(false, scale(Constants.WIDTH), scale(Constants.HEIGHT));
    }

    private void createJoint(int scaleB){
        if(joint != null){
            world.destroyJoint(joint);
        }

        revoluteJoint = new RevoluteJoint(pivot, spike, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, 180);
        joint = revoluteJoint.CreateJoint(world);
    }

    public Body CreateCircleBody(World world, BodyDef.BodyType bodyType, float posx, float posy,
                                 float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(posx, posy);
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
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debuger.render(world, cam.combined);
        game.getSpriteBatch().begin();
        font.draw(game.getSpriteBatch(), "Count: " + count, 30, 450);
        game.getSpriteBatch().end();
    }

    @Override
    public void dispose() {

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
            System.out.println(outside);
            createJoint(50);
        } else {
            System.out.println(outside);
            createJoint(70);
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
