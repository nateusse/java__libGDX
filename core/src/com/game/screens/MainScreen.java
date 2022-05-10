package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.GameAssetManager;
import com.game.MyGdxGame;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MainScreen  extends BaseScreen {

    private static final float WORLD_WIDTH = 10;
    private static final float WORLD_HEIGHT = 10;
    MyGdxGame game;
    LevelScreen levelScreen;
    GameAssetManager assetManager;

    private SpriteBatch batch;
    Sprite spriteBackground;
    OrthographicCamera camera;
    public MainScreen(MyGdxGame game) {
        super(game, "Menú");
        this.game = game;
        assetManager = new GameAssetManager();
        assetManager.loadImages();
        assetManager.manager.finishLoading();

        TextureAtlas textureAtlas = assetManager.manager.get("menus.atlas");
        TextureRegion menuScreen = textureAtlas.findRegion("title");
        spriteBackground = new Sprite(menuScreen);

        levelScreen = new LevelScreen(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getBatch().draw(spriteBackground,0,0,1000,600);

        game.getBatch().end();

        if(Gdx.input.isTouched()){
            game.setScreen(new LevelScreen(game));

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
