package com.aivars.firstgame.levels.first.handlers;

import com.aivars.firstgame.levels.Level;
import com.aivars.firstgame.levels.first.FirstLevel;
import com.aivars.firstgame.states.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import static com.aivars.firstgame.Constants.BALL_INNER_LENGTH;
import static com.aivars.firstgame.Constants.BALL_OUTER_LENGTH;

public class InputHandler implements InputProcessor {

    private FirstLevel firstLevel;

    public InputHandler(FirstLevel firstLevel) {
        this.firstLevel = firstLevel;
    }

    @Override
    public boolean keyDown(int keycode) {
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
        if (firstLevel.isBallOutside()) {
            firstLevel.createJoint(BALL_INNER_LENGTH);
        } else {
            firstLevel.createJoint(BALL_OUTER_LENGTH);
        }

        firstLevel.setBallOutside(!firstLevel.isBallOutside());
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
