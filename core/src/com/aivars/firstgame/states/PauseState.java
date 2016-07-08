package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseState extends State {

    private Stage stage = new Stage();

    public PauseState(final StateHandler stateHandler) {
        super(stateHandler);
        Gdx.input.setInputProcessor(stage);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.BLACK;
        TextButton textButton = new TextButton("RESTART", textButtonStyle);
        textButton.setPosition(Constants.WIDTH / 3, Constants.HEIGHT / 2);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stateHandler.setState(StateHandler.StateName.GAME);
            }
        });

        stage.addActor(textButton);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255 / 255f, 252 / 255f, 252 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        stage.draw();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        font.setColor(Color.BLACK);
        font.draw(application.getSpriteBatch(), "SCORE: " + (GameState.getCircleCount() == -1 ? 0 : GameState.getCircleCount()), 40 , Constants.HEIGHT - 40);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
