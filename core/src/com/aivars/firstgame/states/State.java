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
    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected AssetHandler assetHandler;
    protected PreferencesHandler preferencesHandler;
    protected BitmapFont bitmapFont = new BitmapFont();

    public State() {
        this.application = StateHandler.getApplication();
        this.spriteBatch = application.getSpriteBatch();
        this.camera = application.getCamera();
        this.assetHandler = application.getAssetHandler();
        this.preferencesHandler = application.getPreferencesHandler();
    }

    public void resize(int w, int h) {
        camera.setToOrtho(false, w, h);
    }

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();

    public Application getApplication() {
        return application;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public AssetHandler getAssetHandler() {
        return assetHandler;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public PreferencesHandler getPreferencesHandler() {
        return preferencesHandler;
    }
}
