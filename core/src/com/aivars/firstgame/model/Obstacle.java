package com.aivars.firstgame.model;

import com.badlogic.gdx.physics.box2d.Body;

public class Obstacle {
    private float x;
    private float y;
    private float sensorX;
    private float sensorY;
    private float angle;
    private Body body;
    private Body sensorBody;
    private boolean destroyable = false;

    public Obstacle(float x, float y,float sensorX,float sensorY, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.sensorX = sensorX;
        this.sensorY = sensorY;
    }

    public Body getSensorBody() {
        return sensorBody;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public void setDestroyable(boolean destroyable) {
        this.destroyable = destroyable;
    }

    public void setSensorBody(Body sensorBody) {
        this.sensorBody = sensorBody;
    }

    public float getSensorX() {
        return sensorX;
    }

    public void setSensorX(float sensorX) {
        this.sensorX = sensorX;
    }

    public float getSensorY() {
        return sensorY;
    }

    public void setSensorY(float sensorY) {
        this.sensorY = sensorY;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
