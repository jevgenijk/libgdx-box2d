package com.aivars.firstgame.handlers;

import com.badlogic.gdx.physics.box2d.*;

public class ContactHandler implements ContactListener {

    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        System.out.println(fa.getUserData() + ", " + fb.getUserData());

    }

    public void endContact(Contact c) {

    }

    public void preSolve(Contact c, Manifold m) {
    }

    public void postSolve(Contact c, ContactImpulse ci) {
    }

}
