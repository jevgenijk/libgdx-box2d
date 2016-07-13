package com.aivars.firstgame.states;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.Constants;
import com.aivars.firstgame.levels.FirstLevel;
import com.aivars.firstgame.levels.Level;
import com.aivars.firstgame.utils.BodyFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.Constants.DEBUG_MODE;
import static com.aivars.firstgame.Constants.GRAVITY;

public class GameState extends State {

    /**
     * TODO:
     * 1. Multiple levels - level screen
     * 2. Refactoring
     */
    private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
    private BodyFactory bodyFactory;
    private World world;
    private Level level;
    private Array<Body> removableBodies = new Array<Body>();

    public GameState(Application application) {
        super(application);
        world = new World(new Vector2(0, GRAVITY), true);
        bodyFactory = new BodyFactory(world);
        level = new FirstLevel(this);
        world.setContactListener((ContactListener) level);
        Gdx.input.setInputProcessor((InputProcessor) level);
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
        level.update(dt);
        disposeBodies();
    }

    @Override
    public void render() {
        level.render();

        if (DEBUG_MODE) {
            debugger.render(world, application.getCamera().combined.scl(Constants.PPM));
        }
        application.getCamera().update();
        application.getSpriteBatch().setProjectionMatrix(application.getCamera().combined);
    }

    @Override
    public void dispose() {
        if (!world.isLocked()) {
            world.dispose();
        }
    }

    public World getWorld() {
        return world;
    }

    public BodyFactory getBodyFactory() {
        return bodyFactory;
    }

    private void disposeBodies() {
        for (int i = 0; i < removableBodies.size; i++) {
            world.destroyBody(removableBodies.get(i));
        }
        removableBodies.clear();
    }

    public void addRemovableBody(Body body) {
        this.removableBodies.add(body);
    }
}
