package com.aivars.firstgame;

import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.aivars.firstgame.Constants.SCALE;

public class Application extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    private StateHandler stateHandler;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
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
}