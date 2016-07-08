package com.aivars.firstgame.handlers;

import com.aivars.firstgame.Application;
import com.aivars.firstgame.states.GameState;
import com.aivars.firstgame.states.SplashState;
import com.aivars.firstgame.states.State;

import java.util.Stack;

import static com.aivars.firstgame.handlers.StateHandler.StateName.SPLASH;

public class StateHandler {

    private Application application;
    private Stack<State> states;

    public StateHandler(Application application) {
        this.application = application;
        this.states = new Stack<State>();
        this.setState(SPLASH);
    }

    public Application getApplication() {
        return application;
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

    private State getState(StateName state) {
        switch (state) {
            case SPLASH:
                return new GameState(this); // SplashState
            case GAME:
                return new GameState(this);
        }

        return null;
    }

    public void setState(StateName state) {
        if (states.size() >= 1) {
            states.pop().dispose();
        }
        states.push(getState(state));
    }

    public enum StateName {
        SPLASH,
        GAME
    }

}
