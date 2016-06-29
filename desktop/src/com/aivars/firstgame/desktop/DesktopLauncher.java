package com.aivars.firstgame.desktop;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.WIDTH;
        config.height = Constants.HEIGHT;
        new LwjglApplication(new Application(), config);


    }
}
