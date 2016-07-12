package com.aivars.firstgame.handlers;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.states.*;

import java.util.Stack;

import static com.aivars.firstgame.handlers.StateHandler.StateName.SPLASH;

public class StateHandler {

    private static Stack<State> states;
    private static Application application;

    public StateHandler(Application app) {
        application = app;
        states = new Stack<State>();
        setState(SPLASH);
    }

    public static State getState(StateName state) {
        switch (state) {
            case SPLASH:
                return new GameState();//SplashState
            case START:
                return new GameState();//StartState
            case GAME:
                return new GameState();
            case GAME_OVER:
                return new GameState();//GameOverState
        }

        return null;
    }

    public static Application getApplication(){
        return application;
    }

    public static void setState(StateName state) {
        if (states.size() >= 1) {
            states.pop().dispose();
        }
        states.push(getState(state));
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render() {
        states.peek().render();
    }

    public void dispose() {
        for (State state : states) {
            state.dispose();
        }
        states.clear();
    }

    public void resize(int w, int h) {
        states.peek().resize(w, h);
    }

    public enum StateName {
        SPLASH,
        START,
        GAME,
        GAME_OVER
    }

}
