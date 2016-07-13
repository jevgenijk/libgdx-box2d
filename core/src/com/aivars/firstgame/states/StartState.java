package com.aivars.firstgame.states;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.StateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class StartState extends State {

    private Stage stage = new Stage();

    public StartState(Application application) {
        super(application);
        Gdx.input.setInputProcessor(stage);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = application.getBitmapFont();
        textButtonStyle.fontColor = Color.BLACK;
        TextButton textButton = new TextButton("START", textButtonStyle);
        textButton.setPosition(Constants.WIDTH/3, Constants.HEIGHT/2);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            StateHandler.setState(StateHandler.StateName.GAME);
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

        application.getSpriteBatch().setProjectionMatrix(application.getCamera().combined);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
