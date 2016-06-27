package com.aivars.firstgame.states;

import com.aivars.firstgame.handlers.GameStateHandler;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayState extends GameState{

    private BitmapFont font = new BitmapFont();

    public PlayState(GameStateHandler gameStateHandler) {
        super(gameStateHandler);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb,"play",100,100);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
