package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.GameStateHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class Play extends GameState {

    Texture texture;
    Sprite sprite;


    public Play(GameStateHandler gameStateHandler) {
        super(gameStateHandler);
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        sprite = new Sprite(texture);
        sprite.setSize(100,100);
        sprite.setPosition(Constants.WIDTH/2,Constants.HEIGHT/2);
        cam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
    }


    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
    }


    int i=0;
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.setOrigin(200,200);
        sprite.setRotation(i++);
        game.getSpriteBatch().begin();
        sprite.draw(game.getSpriteBatch());
        game.getSpriteBatch().end();
    }

    @Override
    public void dispose() {

    }
}
