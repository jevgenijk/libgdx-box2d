package com.aivars.firstgame.levels.first.handlers;

import com.aivars.firstgame.levels.first.FirstLevel;
import com.badlogic.gdx.InputProcessor;

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
            firstLevel.createJoint(firstLevel.getBallInnerLength());
        } else {
            firstLevel.createJoint(firstLevel.getBallOuterLength());
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
