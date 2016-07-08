package com.aivars.firstgame.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AssetHandler {
    private AssetManager assetManager = new AssetManager();
    private Map<String, Sprite> sprites = new HashMap<String, Sprite>();

    public void addSprite(String fileName) {
        assetManager.load(fileName, Texture.class);
        assetManager.finishLoading();
        sprites.put(fileName, new Sprite((Texture) assetManager.get(fileName)));
    }

    public Sprite getSprite(String fileName) {
        return sprites.get(fileName);
    }

    public void dispose() {
        Array<String> assetNames = assetManager.getAssetNames();
        for (String assetName : assetNames) {
            assetManager.unload(assetName);
        }
        sprites.clear();
    }

    public void load(){
        dispose();
        addSprite("ball.png");
        addSprite("obstacle.png");
        addSprite("circle.png");
    }

    public Map<String,Sprite> getSprites(){
        return sprites;
    }

}
