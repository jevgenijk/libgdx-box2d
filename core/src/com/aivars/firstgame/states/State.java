package com.aivars.firstgame.states;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    protected StateHandler stateHandler;
    protected Application application;
    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;

    protected State(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.application = stateHandler.getApplication();
        this.spriteBatch = application.getSpriteBatch();
        this.camera = application.getCamera();
    }

    public void resize(int w, int h) {
        camera.setToOrtho(false, w, h);
    }

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();
}
