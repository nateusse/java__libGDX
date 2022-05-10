package com.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameAssetManager {

    public final AssetManager manager = new AssetManager();
    public final String images = "menus.atlas";

    public void loadImages(){
        manager.load(images, TextureAtlas.class);
    }

    public void loadNinja(){
        manager.load(images, TextureAtlas.class);
    }
}
