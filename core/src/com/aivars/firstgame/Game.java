package com.aivars.firstgame;

import com.aivars.firstgame.handlers.GameStateHandler;
import com.aivars.firstgame.handlers.InputHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

    private static final float STEP = 1 / 60f;
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private float deltaTime;

    private GameStateHandler gameStateHandler;
    private InputHandler inputHandler;


    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    public void create() {
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        gameStateHandler = new GameStateHandler(this);
        inputHandler = new InputHandler();
    }

    @Override
    public void render() {
        deltaTime += Gdx.graphics.getDeltaTime();
        while (deltaTime >= STEP) {
            deltaTime -= STEP;
            gameStateHandler.update(STEP);
            gameStateHandler.render();
        }
    }

    @Override
    public void dispose() {

    }
}