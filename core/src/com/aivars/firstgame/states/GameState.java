package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.levels.first.FirstLevel;
import com.aivars.firstgame.levels.Level;
import com.aivars.firstgame.utils.BodyFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.Constants.*;
import static com.aivars.firstgame.utils.Utils.scale;

public class GameState extends State {

    /**
     * TODO:
     * 1. Multiple levels - level screen
     * 3. Randomize obstacle distance
     * 4. Refactoring
     */
    private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
    private BodyFactory bodyFactory;
    private World world;
    private Level level;


    public GameState() {
        super();
        world = new World(new Vector2(0, GRAVITY), true);
        bodyFactory = new BodyFactory(world);
        level = new FirstLevel(this);
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
        disposeBodies();
        level.update(dt);
    }

    @Override
    public void render() {
        level.render();

        if (DEBUG_MODE) {
            debugger.render(world, getCamera().combined.scl(Constants.PPM));
        }
        getCamera().update();
        getSpriteBatch().setProjectionMatrix(getCamera().combined);
    }

    @Override
    public void dispose() {
        if (!world.isLocked()) {
            world.dispose();
        }

        level.dispose();
    }

    public World getWorld(){
        return world;
    }

    public BodyFactory getBodyFactory(){
        return bodyFactory;
    }

    private void disposeBodies() {
        Array<Body> bodies = level.getContactHandler().getRemovableBodies();
        for (int i = 0; i < bodies.size; i++) {
            world.destroyBody(bodies.get(i));
        }
        bodies.clear();
    }
}
