package com.aivars.firstgame.handlers;

import com.aivars.firstgame.model.BodyUserData;
import com.aivars.firstgame.model.Obstacle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.states.GameState.obstacles;

public class ContactHandler implements ContactListener {

    private Array<Body> removableBodies;

    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.isSensor()) {
            if (fa.getBody().getUserData() != null) {
                BodyUserData userData = (BodyUserData) fa.getBody().getUserData();
                userData.setDisposable(true);
                fa.getBody().setUserData(userData);
            }
        }

        if (fb.isSensor()) {
            if (fb.getBody().getUserData() != null) {
                BodyUserData userData = (BodyUserData) fb.getBody().getUserData();
                userData.setDisposable(true);
                fb.getBody().setUserData(userData);
            }
        }

    }

    public void endContact(Contact c) {

    }

    public void preSolve(Contact c, Manifold m) {

    }

    public void postSolve(Contact c, ContactImpulse ci) {

    }

}
