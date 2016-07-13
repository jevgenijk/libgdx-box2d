package com.aivars.firstgame;

import com.aivars.firstgame.handlers.AssetHandler;
import com.aivars.firstgame.handlers.PreferencesHandler;
import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Application extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private AssetHandler assetHandler;
    private StateHandler stateHandler;
    private PreferencesHandler preferencesHandler;
    private BitmapFont bitmapFont;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        camera = new OrthographicCamera();
        assetHandler = new AssetHandler();
        preferencesHandler = new PreferencesHandler(Gdx.app.getPreferences("Billion dollar app"));
        stateHandler = new StateHandler(this);
    }

    @Override
    public void render() {
        stateHandler.update(Gdx.graphics.getDeltaTime());
        stateHandler.render();
    }

    @Override
    public void dispose() {
        stateHandler.dispose();
        spriteBatch.dispose();
        assetHandler.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stateHandler.resize(width, height);
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

    public PreferencesHandler getPreferencesHandler() {
        return preferencesHandler;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }
}