package com.aivars.firstgame.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class ContactHandler implements ContactListener {

    private Array<Body> removableBodies;

    public ContactHandler() {
        super();
        removableBodies = new Array<Body>();
    }

    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.isSensor()) {
            if (fa.getBody().getUserData() != null) {
                Body userData = (Body) fa.getBody().getUserData();
                removableBodies.add(userData);
                removableBodies.add(fa.getBody());
            }
        }

        if (fb.isSensor()) {
            if (fb.getBody().getUserData() != null) {
                Body userData = (Body) fb.getBody().getUserData();
                removableBodies.add(userData);
                removableBodies.add(fb.getBody());
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
