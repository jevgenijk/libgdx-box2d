package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class SplashState extends State {

    float accumulate = 0f;

    public SplashState(StateHandler stateHandler) {
        super(stateHandler);
    }

    @Override
    public void update(float dt) {
        accumulate += dt;
        if (accumulate >= 1) {
            stateHandler.setState(StateHandler.StateName.START);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255 / 255f, 252 / 255f, 252 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        font.setColor(Color.BLACK);
        font.draw(application.getSpriteBatch(), "NASTY FIXES GO GO GO", Constants.WIDTH / 4, Constants.HEIGHT / 2);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
