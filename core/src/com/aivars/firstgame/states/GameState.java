package com.aivars.firstgame.states;

import com.aivars.firstgame.Game;
import com.aivars.firstgame.handlers.GameStateHandler;
import com.aivars.firstgame.handlers.InputHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {
    protected GameStateHandler gameStateHandler;
    protected Game game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected InputHandler inputHandler;

    protected GameState(GameStateHandler gameStateHandler) {
        this.gameStateHandler = gameStateHandler;
        game = gameStateHandler.getGame();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        inputHandler = game.getInputHandler();
    }

    public abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();
}
