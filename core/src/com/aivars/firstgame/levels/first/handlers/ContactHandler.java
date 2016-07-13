package com.aivars.firstgame.levels.first.handlers;

import com.aivars.firstgame.handlers.StateHandler;
import com.aivars.firstgame.levels.Level;
import com.aivars.firstgame.levels.first.FirstLevel;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class ContactHandler implements ContactListener {

    private Array<Body> removableBodies;
    private FirstLevel firstLevel;

    public ContactHandler(FirstLevel firstLevel) {
        super();
        this.firstLevel = firstLevel;
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
                firstLevel.increaseCircleCount();
            } else if (fa.getBody().getUserData().equals("obstacle")) {
                StateHandler.setState(StateHandler.StateName.GAME_OVER);
            }
        }

        if (fb.getBody().getUserData() != null) {
            if (fb.isSensor()) {
                Body userData = (Body) fb.getBody().getUserData();
                removableBodies.add(userData);
                removableBodies.add(fb.getBody());
                firstLevel.increaseCircleCount();
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
