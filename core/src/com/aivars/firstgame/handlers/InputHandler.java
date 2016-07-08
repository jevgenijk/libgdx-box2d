package com.aivars.firstgame.handlers;

import com.aivars.firstgame.states.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import static com.aivars.firstgame.Constants.BALL_INNER_LENGTH;
import static com.aivars.firstgame.Constants.BALL_OUTER_LENGTH;
import static com.aivars.firstgame.states.GameState.isBallOutside;
import static com.aivars.firstgame.states.GameState.setBallOutside;

public class InputHandler implements InputProcessor {

    private GameState gameState;

    public InputHandler(GameState state) {
        this.gameState = state;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            System.out.println("left");

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            System.out.println("right");
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isBallOutside()) {
            gameState.createJoint(BALL_INNER_LENGTH);
        } else {
            gameState.createJoint(BALL_OUTER_LENGTH);
        }

        setBallOutside(!isBallOutside());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
