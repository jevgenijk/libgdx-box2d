package com.aivars.firstgame.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.states.GameState.increaseCircleCount;


public class ContactHandler implements ContactListener {

    private Array<Body> removableBodies;

    public ContactHandler() {
        super();
        removableBodies = new Array<Body>();
    }

    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null) {
            if (fa.isSensor()) {
                Body userData = (Body) fa.getBody().getUserData();
                removableBodies.add(userData);
                removableBodies.add(fa.getBody());
                increaseCircleCount();
            } else if (fa.getBody().getUserData().equals("obstacle")) {
                StateHandler.setState(StateHandler.StateName.GAME_OVER);
            }
        }

        if (fb.getBody().getUserData() != null) {
            if (fb.isSensor()) {
                Body userData = (Body) fb.getBody().getUserData();
                removableBodies.add(userData);
                removableBodies.add(fb.getBody());
                increaseCircleCount();
            } else if (fb.getBody().getUserData().equals("obstacle")) {
                StateHandler.setState(StateHandler.StateName.GAME_OVER);
            }
        }
    }

    public Array<Body> getRemovableBodies() {
        return removableBodies;
    }

    public void endContact(Contact c) {

    }

    public void preSolve(Contact c, Manifold m) {

    }

    public void postSolve(Contact c, ContactImpulse ci) {

    }

}
