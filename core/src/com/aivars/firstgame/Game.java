package com.aivars.firstgame;

import com.aivars.firstgame.handlers.GameStateHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

    private static final float STEP = 1 / 60f;
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private float accumulator;

    private GameStateHandler gameStateHandler;

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }


    @Override
    public void create() {
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        gameStateHandler = new GameStateHandler(this);
    }

    @Override
    public void render() {
        accumulator  += Gdx.graphics.getDeltaTime();
        while (accumulator  >= STEP) {
            accumulator  -= STEP;
            gameStateHandler.update(STEP);
            gameStateHandler.render();
        }
    }

    @Override
    public void dispose() {

    }
}