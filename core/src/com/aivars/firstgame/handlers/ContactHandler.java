package com.aivars.firstgame.handlers;

import com.aivars.firstgame.model.BodyUserData;
import com.aivars.firstgame.model.Obstacle;
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
                BodyUserData userData = (BodyUserData) fa.getBody().getUserData();
                removableBodies.add(userData.getObstacle());
                removableBodies.add(fa.getBody());
            }
        }

        if (fb.isSensor()) {
            if (fb.getBody().getUserData() != null) {
                BodyUserData userData = (BodyUserData) fb.getBody().getUserData();
                removableBodies.add(userData.getObstacle());
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
