package com.aivars.firstgame.handlers;

import com.badlogic.gdx.Preferences;

public class PreferencesHandler {
    private Preferences preferences;

    public PreferencesHandler(Preferences preferences) {
        this.preferences = preferences;
    }

    public Integer getInteger(String key) {
        return preferences.getInteger(key);
    }

    public void putInteger(String key, Integer value) {
        preferences.putInteger(key, value);
        preferences.flush();
    }

    public void dispose(){
        preferences.clear();
        preferences.flush();
    }
}
