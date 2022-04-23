package com.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AtlasTexture {

    private static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas("tuto.atlas");
    }

    public void dispose() {
        atlas.dispose();
    }
}
