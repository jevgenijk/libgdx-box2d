package com.aivars.firstgame.model;

import com.badlogic.gdx.physics.box2d.Body;

public class BodyUserData {
    private boolean disposable = false;
    private Body obstacle;

    public BodyUserData(Body obstacle) {
        this.obstacle = obstacle;
    }

    public Body getObstacle() {
        return obstacle;
    }

    public void setObstacle(Body obstacle) {
        this.obstacle = obstacle;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public void setDisposable(boolean disposable) {
        this.disposable = disposable;
    }
}
