package com.aivars.firstgame.states;

import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class SplashState extends State {

    float accumulate = 0f;
    Texture texture;

    public SplashState(StateHandler stateHandler) {
        super(stateHandler);
        texture = new Texture("badlogic.jpg");
    }

    @Override
    public void update(float dt) {
        accumulate += dt;
        if(accumulate >= 3){
            stateHandler.setState(StateHandler.StateName.GAME);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(texture,Gdx.graphics.getWidth() / 4 - texture.getWidth() / 2, Gdx.graphics.getHeight() / 4 - texture.getWidth() / 2);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
