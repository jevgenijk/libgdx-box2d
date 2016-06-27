package com.aivars.firstgame;

import com.aivars.firstgame.handlers.GameStateHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudCam;

    private static final float STEP = 1 /60f;
    private float accum;

    private GameStateHandler gameStateHandler;

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getHudCamera() {
        return hudCam;
    }

    @Override
    public void create() {
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false,Constants.WIDTH,Constants.HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false,Constants.WIDTH,Constants.HEIGHT);
        gameStateHandler = new GameStateHandler(this);
    }

    @Override
    public void render() {
        accum += Gdx.graphics.getDeltaTime();
        while(accum >= STEP){
            accum -= STEP;
            gameStateHandler.update(STEP);
            gameStateHandler.render();
        }
    }

    @Override
    public void dispose() {

    }
}