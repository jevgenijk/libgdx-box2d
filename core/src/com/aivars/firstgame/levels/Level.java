package com.aivars.firstgame.levels;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.handlers.AssetHandler;
import com.aivars.firstgame.handlers.PreferencesHandler;
import com.aivars.firstgame.states.GameState;
import com.aivars.firstgame.utils.BodyFactory;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Level {

    protected GameState gameState;
    protected Application application;
    protected World world;
    protected BodyFactory bodyFactory;
    protected SpriteBatch spriteBatch;
    protected AssetHandler assetHandler;
    protected BitmapFont bitmapFont;
    protected PreferencesHandler preferencesHandler;

    public Level(GameState gameState) {
        this.world = gameState.getWorld();
        this.gameState = gameState;
        this.application = gameState.getApplication();
        this.bodyFactory = gameState.getBodyFactory();
        this.spriteBatch = gameState.getSpriteBatch();
        this.assetHandler = gameState.getAssetHandler();
        this.bitmapFont = gameState.getBitmapFont();
        this.preferencesHandler = gameState.getPreferencesHandler();
    }

    public abstract void update(float dt);

    public abstract void render();
}
