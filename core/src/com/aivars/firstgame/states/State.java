package com.aivars.firstgame.states;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.handlers.AssetHandler;
import com.aivars.firstgame.handlers.PreferencesHandler;
import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    protected Application application;

    public State(Application application) {
        this.application = application;
    }

    public void resize(int w, int h) {
        application.getCamera().setToOrtho(false, w, h);
    }

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();

    public Application getApplication() {
        return application;
    }
}
