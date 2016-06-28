package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.GameStateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PlayState extends GameState{

    private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer debuger;

    public PlayState(GameStateHandler gameStateHandler) {
        super(gameStateHandler);
        world = new World(new Vector2(0,-9.81f),true);
        world.setContactListener(new ContactHandler());
        debuger = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        bdef.position.set(160 / Constants.PTM, 120 / Constants.PTM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / Constants.PTM, 5 / Constants.PTM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("ground");

        bdef.position.set(160 / Constants.PTM, 200 / Constants.PTM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        shape.setAsBox(5 / Constants.PTM, 5 / Constants.PTM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("box");

        cam.setToOrtho(false, Constants.WIDTH / Constants.PTM, Constants.HEIGHT / Constants.PTM);
    }

    @Override
    public void handleInput() {
        
    }

    @Override
    public void update(float dt) {
        world.step(dt,6,2);
        handleInput();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debuger.render(world, cam.combined);
    }

    @Override
    public void dispose() {

    }
}
