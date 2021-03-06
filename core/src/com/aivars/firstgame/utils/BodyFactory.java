package com.aivars.firstgame.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.aivars.firstgame.utils.Utils.scale;

public class BodyFactory {

    private World world;

    public BodyFactory(World world) {
        this.world = world;
    }

    public Body createRectangle(BodyDef.BodyType type, Vector2 position, float angle, float width, float height, Object userData, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(position.x, position.y);
        bodyDef.angle = angle;

        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);
        fixtureDef.shape = poly;
        fixtureDef.isSensor = isSensor;
        body.createFixture(fixtureDef);

        body.setUserData(userData);
        return body;
    }

    public Body createCircle(BodyDef.BodyType bodyType, Vector2 position, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position.x, position.y);

        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.setRadius(scale(radius));

        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();
        return body;
    }

    public Body createCircle(BodyDef.BodyType bodyType, Vector2 position, float radius, boolean isSensor,String userData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position.x, position.y);

        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.setRadius(scale(radius));
        fixtureDef.isSensor = isSensor;
        body.setUserData(userData);

        MassData massData = new MassData();
        massData.I = (float) 1;
        body.setMassData(massData);

        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();
        return body;
    }

}
